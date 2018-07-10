package org.foresee.intelli.algo.genetic;

import java.util.Random;

public class GeneticAlgo {
	Random random=new Random();
	/**
	 * 轮盘赌，随机一个0到1之间的数，probability数组中概率看成顺序排列的桶，累加直到大于随机的数，放在这个桶里
	 * @return
	 */
	public int roulette(double[] probability) {
		double rand=random.nextDouble();
		int pos=0;
		double sum=probability[0];
		while (sum<rand) {
			pos++;
			sum+=probability[pos];
		}
		return pos;
	}
	/**
	 * 给出一组适应性数值，计算总和，求每个数值占该总和的概率，返回同顺序的概率数组。
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
