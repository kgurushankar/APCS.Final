package server;

import common.Map;

/**
 * Generate map
 * 
 * @author kgurushankar
 * @version 18.5.16
 */
public class MapGenerator {
	/**
	 * This method works better when the map is larger, and generates the map as
	 * smaller pieces and joins them together
	 * 
	 * @param sideLength
	 *            has to be divisible by 2
	 * @return a randomly generated map
	 */
	public static Map generateMap(int sideLength) {
		sideLength /= 2;
		if (sideLength <= 100) {
			return new Map(change(generateMapPart(sideLength, sideLength)));
		} else {
			int[][] state = new int[sideLength][sideLength];
			int subcomponents = (sideLength + 99) / 100; // round up always
			for (int i = 0; i < subcomponents; i++) {
				for (int j = 0; j < subcomponents; j++) {
					int[][] a = generateMapPart(100, 100);
					for (int k = i * 100; k < (i + 1) * 100 && k < sideLength; k++) {
						for (int l = j * 100; l < (j + 1) * 100 && l < sideLength; l++) {
							state[k][l] = a[k - i * 100][l - j * 100];
						}
					}
				}
			}
			// TODO check seams...

			return new Map(change(state));
		}
	}

	private static int[][] generateMapPart(int width, int height) {

		int[][] state = new int[height][width];
		int obstacles = (int) (Math.random() * height * width / 8d + height * width / 4d);// tune this properly
		for (int i = 0; i < obstacles; i++) {
			int a = (int) (Math.random() * height);
			int b = (int) (Math.random() * width);
			if (state[a][b] == 0) {
				state[a][b] = -1;// wall
			} else {
				i--;
			}
		}

		// Do DFS
		int c = 2;
		int val = 3;
		int max = -1;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (state[i][j] == 0) {
					int x = DFS(state, i, j, c);
					if (x > max) {
						val = c;
						max = x;
					}
					c++;
				}
			}
		}
		// Fill
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (state[i][j] == val) {
					state[i][j] = 0;
				} else if (state[i][j] != -1) {
					state[i][j] = -1;
				}
			}
		}
		return state;
	}

	private static int DFS(int[][] state, int a, int b, int val) {
		if (a >= state.length || a < 0 || b >= state[a].length || b < 0 || state[a][b] == -1) { // wall
			return 0;
		} else if (state[a][b] != 0) { // another things set
			return 0;
		} else {
			state[a][b] = val;
			return 1 + DFS(state, a + 1, b, val) + DFS(state, a - 1, b, val) + DFS(state, a, b + 1, val)
					+ DFS(state, a, b - 1, val);
		}
	}

	private static boolean[][] change(int[][] state) {
		boolean[][] out = new boolean[state.length * 2][];
		for (int i = 0; i < state.length * 2; i++) {
			out[i] = new boolean[state[i / 2].length * 2];
			for (int j = 0; j < state[i / 2].length * 2; j++) {
				out[i][j] = state[i / 2][j / 2] == -1;
			}
		}
		return out;
	}
}
