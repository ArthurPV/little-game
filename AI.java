package project.ai;

import java.util.HashMap;
import project.turn.Turn;
import base.vector.Vector;

public class AI {
    private int player;
	private Vector<Vector<Integer>> allCombinaisons;

    public AI(int player) {
		this.player = player;
		this.allCombinaisons = AI.generateAllCombinaisons();
    }

    private static Vector<Vector<Integer>> generateAllCombinaisons() {
        var res = new Vector<Vector<Integer>>();

			for (int i = 0; i <= 3; i++) {
				for (int j = 0; j <= 3; j++) {
					for (int k = 0; k <= 3; k++) {
						for (int l = 0; l <= 3; l++) {
							for (int m = 0; m <= 3; m++) {
								for (int n = 0; n <= 3; n++) {
									for (int o = 0; o <= 3; o++) {
										for (int p = 0; p <= 3; p++) {
											for (int q = 0; q <= 3; q++) {
												for (int r = 0; r <= 3; r++) {
													for (int s = 0; s <= 3; s++) {
														for (int t = 0; t <= 3; t++) {
															for (int u = 0; u <= 3; u++) {
																var v = new Vector<Integer>();
																var count = i + j + k + l;

																if (i != 0) {
																	v.push(i);	
																}

																if (j != 0) {
																	v.push(j);
																}

																if (k != 0) {
																	v.push(k);
																}

																if (l != 0) {
																	v.push(l);
																}

																count += m;

																if (count <= 13 && m != 0) {
																	v.push(m);
																} else if (count > 13) {
																	continue;
																}

																count += n;

																if (count <= 13 && m != 0) {
																	v.push(n);
																} else if (count > 13) {
																	continue;
																}

																count += o;

																if (count <= 13 && o != 0) {
																	v.push(o);
																} else if (count > 13) {
																	continue;
																}

																count += p;

																if (count <= 13 && p != 0) {
																	v.push(p);
																} else if (count > 13) {
																	continue;
																}

																count += q;

																if (count <= 13 && q != 0) {
																	v.push(q);
																} else if (count > 13) {
																	continue;
																}

																count += r;

																if (count <= 13 && r != 0) {
																	v.push(r);
																} else if (count > 13) {
																	continue;
																}

																count += s;

																if (count <= 13 && s != 0) {
																	v.push(s);
																} else if (count > 13) {
																	continue;
																}

																count += t;

																if (count <= 13 && t != 0) {
																	v.push(t);
																} else if (count > 13) {
																	continue;
																}

																count += u;

																if (count <= 13 && u != 0) {
																	v.push(u);
																} else if (count > 13) {
																	continue;
																}

																if (Vector.reduce(v) == 13) {
																	res.push(v);
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}


        return res;
    }

	public void setPlayer(int player) {
		this.player = player;
	}

	public int getPlayer() {
		return this.player;
	}

    public double calcGeneralAverage() {
		int win = 0;
		int lose = 0;

		if (player == 1) {
			for (Vector<Integer> comb : allCombinaisons) {
				if (comb.len() % 2 == 0) {
					win++;
				} else {
					lose++;
				}
			}
		} else {
			for (Vector<Integer> comb : allCombinaisons) {
				if (comb.len() % 2 == 0) {
					lose++;
				} else {
					win++;
				}
			}
		}

		int total = win + lose;

		return ((double)win / (double)(total == 0 ? 1 : total)) * 100;
    }

	public double calcAverage(int countTurn, int n, int countMatchstick) {
		int win = 0;
		int lose = 0;

		if (player == 1) {
			for (Vector<Integer> comb : allCombinaisons) {
				if (comb.len() > countTurn) {
					if (comb.get(countTurn) == n) {
						if (comb.len() % 2 != 0 && countMatchstick - n > 1 && comb.get(countTurn) == n) {
							win++;
						} else if (comb.len() % 2 != 0 && countMatchstick - n == 1 && comb.get(countTurn) == n) {
							win += 100;
						} else if (comb.len() % 2 == 0 && countMatchstick - n == 0 && comb.get(countTurn) == n) {
							lose += 100;
						} else {
							lose++;
						}
					}
				}
			}
		} else {
			for (Vector<Integer> comb : allCombinaisons) {
				if (comb.len() > countTurn) {
					if (comb.len() % 2 == 0 && countMatchstick - n > 1 && comb.get(countTurn) == n) {
						win++;
					} else if (comb.len() % 2 == 0 && countMatchstick - n == 1 && comb.get(countTurn) == n) {
						win += 100;
					} else if (comb.len() % 2 != 0 && countMatchstick - n == 0 && comb.get(countTurn) == n) {
						lose += 100;
					} else {
						lose++;
					}
				}
			}
		}

		int total = win + lose;


		return ((double)win / (double)(total == 0 ? 1 : total)) * 100;
	}

	public int chooseAnOption(int countTurn, int countMatchstick) {
		double average1 = calcAverage(countTurn, 1, countMatchstick);
		double average2 = calcAverage(countTurn, 2, countMatchstick);
		double average3 = calcAverage(countTurn, 3, countMatchstick);

		System.out.println(average1);
		System.out.println(average2);
		System.out.println(average3);

		return average1 > average2 && average1 > average3 ? 1 : average2 > average1 && average2 > average3 ? 2 : 3;
	}

	public void restrictPossibilites(int countTurn, int n, int countMatchstick) {
		this.allCombinaisons = this.allCombinaisons.filter((Vector<Integer> v) -> v.len() > countTurn ? v.get(countTurn) == n : false);
	}

	public int run(int countTurn, int countMatchstick) {
		int option = chooseAnOption(countTurn, countMatchstick);

		restrictPossibilites(countTurn, option, countMatchstick);

		return option;
	}
}
