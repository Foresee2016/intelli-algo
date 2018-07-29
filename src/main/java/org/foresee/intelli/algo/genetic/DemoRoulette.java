package org.foresee.intelli.algo.genetic;

import java.util.Arrays;

public class DemoRoulette {

	public static void main(String[] args) {
		// 试一试轮盘赌这个方法写对了没，结果是[101, 926, 8973]，大致是1:9:90，与适应度呈正比。
		GeneticAlgo geneticAlgo = new GeneticAlgo(12);
		double[] fitness = new double[] { 1, 9, 90 };
		double[] probability = GeneticAlgo.calcProbability(fitness);
		int[] binCnt = new int[fitness.length];
		int iterateTimes = 10000;
		while (iterateTimes-- > 0) {
			int pos = geneticAlgo.roulette(probability);
			binCnt[pos]++;
		}
		System.out.println(Arrays.toString(binCnt));
	}

}
