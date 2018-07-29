package org.foresee.intelli.algo.genetic;

import org.foresee.intelli.algo.genetic.GeneticAlgo.FitnessCalculator;

/**
 * 使用一个实例查看遗传算法运行过程，函数：min( 3 - sin(j*x1)*sin(j*x1) - sin(j*x2)*sin(j*x2))，
 * 当前使用j=2，定义域x1，x2属于[0，6]，应该会有16个最优解，最小值都对应1
 * 精确到小数点后3位，即定义域每1都需要10^3个编码，6对应6*10^3，2的13次方够用，两位需要26位二进制编码，可以用int
 * 改为求最大值，并且都是正值return Math.pow(Math.sin(2*x1), 2) + Math.pow(Math.sin(2*x2), 2) + 2;
 */
public class DemoGenetic {
	public static void main(String[] args) {
		FitnessCalculator calculator=new FitnessCalculator() {
			
			@Override
			public double calcFitness(double x1, double x2) {
				// TODO: 换一个有明确极值点的函数再测试
				return Math.pow(Math.sin(2*x1), 2) + Math.pow(Math.sin(2*x2), 2) + 2;
			}
		};
		GeneticAlgo algo=new GeneticAlgo(26, calculator);
		algo.begin(100, 300);
	}
}
