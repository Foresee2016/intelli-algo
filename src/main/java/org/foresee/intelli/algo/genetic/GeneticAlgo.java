package org.foresee.intelli.algo.genetic;

import java.util.Random;

public class GeneticAlgo {
	Random random=new Random();
	public int rouletteWheelSelection(double[] probability) {
		double rand=random.nextDouble();
		double sum=probability[0];
		int pos=0;
		while (sum<rand) {
			pos++;
			sum+=probability[pos];
		}
		return pos;
	}
	/**
	 * 轮盘赌，给出一组适应性数值，计算总和，求每个数值占该总和的概率，返回同顺序的概率数组。
	 */
	public static double[] calcProbability(double[] fitness) {
		double total=0;
		for (int i = 0; i < fitness.length; i++) {
			total+=fitness[i];
		}
		double[] probability=new double[fitness.length];
		for (int i = 0; i < probability.length; i++) {
			probability[i]=fitness[i]/total;
		}
		return probability;
	}
}
