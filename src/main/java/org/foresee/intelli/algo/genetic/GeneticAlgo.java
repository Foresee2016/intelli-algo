package org.foresee.intelli.algo.genetic;

import java.util.Arrays;
import java.util.Random;

public class GeneticAlgo {
	Random random = new Random();
	final int geneSize;
	FitnessCalculator calculator;

	public GeneticAlgo(int geneSize) {
		super();
		if (geneSize >= 32) {
			throw new RuntimeException("大于32位不能用int编码");
		}
		this.geneSize = geneSize;
	}

	public GeneticAlgo(int geneSize, FitnessCalculator calculator) {
		super();
		if (geneSize >= 32) {
			throw new RuntimeException("大于32位不能用int编码");
		}
		this.geneSize = geneSize;
		this.calculator = calculator;
	}

	public void begin(int chromCount, int iterateTimes) {
		int[] chroms = new int[chromCount];
		for (int i = 0; i < chroms.length; i++) {
			chroms[i] = randomEncode();
		}
		double[] fitnesss = new double[chroms.length];
		Arrays.fill(fitnesss, -1);
		while (iterateTimes > 0) {
			iterateTimes--;
			// 计算适应度
			for (int i = 0; i < chroms.length; i++) {
				double[] params = decode(chroms[i]);
				double fitness = calculator.calcFitness(params[0], params[1]);
				fitnesss[i] = fitness;
			}
			// 轮盘赌，计算适应度对应的可生成下一代的几率
			double[] probability = calcProbability(fitnesss);
			int[] bins = new int[chroms.length];
			for (int i = 0; i < chroms.length; i++) {
				int pos = roulette(probability);
				bins[pos]++;
			}
			// 交叉
			int[] crossParent = new int[chroms.length]; // 生成交叉操作使用的父代，bins里数值越大，占的长度越长
			int curSum = 0;
			for (int i = 0; i < chroms.length; i++) {
				int curNum = bins[i];
				for (int j = 0; j < curNum; j++) {
					crossParent[curSum + j] = chroms[i];
				}
				curSum += curNum;
			}
			int[] next = new int[chroms.length];
			for (int i = 0; i < chroms.length; i += 2) {
				int parent1 = (int) (Math.random() * crossParent.length);
				int parent2 = (int) (Math.random() * crossParent.length);
				int chrom1 = crossParent[parent1];
				int chrom2 = crossParent[parent2];
				int crossPos = (int) (Math.random() * 26);
				int getL = 0, getR = 0;
				for (int j = 0; j < crossPos; j++) {
					getR = getR | (0x1 << j);
				}
				for (int j = crossPos; j < 26; j++) {
					getL = getL | (0x1 << j);
				}
				int chrom1L = chrom1 & getL, chrom1R = chrom1 & getR;
				int chrom2L = chrom2 & getL, chrom2R = chrom2 & getR;
				next[i] = chrom1L | chrom2R;
				next[i + 1] = chrom2L | chrom1R;
			}
			// 变异
			for (int i = 0; i < next.length; i++) {
				double mutationProb = Math.random();
				if (mutationProb < 0.01) {
					int mutationPos = (int) (Math.random() * 26);
					if ((next[i] & (1 << mutationPos)) == 0) { // 本来是0，突变为1
						next[i] = next[i] | 1 << mutationPos;
					} else {
						next[i] = next[i] & (~(1 << mutationPos));
					}
				}
			}
			chroms = next;
		}
		int bestFitIdx = 0;
		for (int i = 1; i < fitnesss.length; i++) {
			if (fitnesss[i] > fitnesss[bestFitIdx]) {
				bestFitIdx = i;
			}
		}
		double[] params = decode(chroms[bestFitIdx]);
		System.out.println("Best Fitness: " + fitnesss[bestFitIdx] + ", x1 = " + params[0] + ", x2 = " + params[1]);
	}

	public int randomEncode() {
		int chrom = 0;
		for (int i = 0; i < geneSize; i++) {
			if (random.nextBoolean()) {
				chrom |= 1;
			}
			chrom = chrom << 1;
		}
		return chrom;
	}

	public double[] decode(int chrom) {
		double[] params = new double[2];
		int x1Part = chrom & 0x1FFF; // 取低13位
		int x2Part = (chrom >> 13) & 0x1FFF; // 取高13位
		params[0] = x1Part / 1000.0d;
		params[1] = x2Part / 1000.0d;
		return params;
	}

	/**
	 * 轮盘赌，随机一个0到1之间的数，probability数组中概率看成顺序排列的桶，累加直到大于随机的数，放在这个桶里
	 * 
	 * @return
	 */
	public int roulette(double[] probability) {
		double rand = random.nextDouble();
		int pos = 0;
		double sum = probability[0];
		while (sum < rand) {
			pos++;
			sum += probability[pos];
		}
		return pos;
	}

	/**
	 * 给出一组适应性数值，计算总和，求每个数值占该总和的概率，返回同顺序的概率数组。
	 */
	public static double[] calcProbability(double[] fitness) {
		double total = 0;
		for (int i = 0; i < fitness.length; i++) {
			total += fitness[i];
		}
		double[] probability = new double[fitness.length];
		for (int i = 0; i < probability.length; i++) {
			probability[i] = fitness[i] / total;
		}
		return probability;
	}

	public interface FitnessCalculator {
		double calcFitness(double x1, double x2);
	}
}
