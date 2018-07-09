package org.foresee.intelli.algo.genetic;

import java.util.Arrays;
import java.util.Random;

public class SeeRandom {
	public static void main(String[] args) {
		seeMathRandom();
		seeDistribution();
		seeSeed();
	}
	/**
	 * 最基础的随机数生成方式，Math.random()
	 */
	public static void seeMathRandom() {
		int count=10;
		for (int i = 0; i < count; i++) {
			double rand=Math.random();
			System.out.println(rand);
		}
	}
	/**
	 * 查看生成的随机数的分布情况，10000个随机数，10个桶，每个桶接收一个区间内的数，查看最后每个桶里数的个数。
	 * 结果：[952, 987, 994, 996, 1017, 1055, 978, 1011, 1010, 1000]，呈均匀分布。
	 */
	public static void seeDistribution() {
		int count=10000;
		int binCount=10;
		int[] bins=new int[binCount];
		double seperate=1.0/binCount;
		Random random=new Random();
		for (int i = 0; i < count; i++) {
			double value=random.nextDouble();
			bins[(int) (value/seperate)]++;
		}
		System.out.println("total: "+count+", bins: "+binCount);
		System.out.println("statictic: "+Arrays.toString(bins));
	}
	/**
	 * 两个使用相同种子值的随机数对象，每次将产生相同的随机数。
	 */
	public static void seeSeed() {
		int count=10;
		Random random1=new Random(100);
		Random random2=new Random(100);
		for (int i = 0; i < count; i++) {
			double rand1=random1.nextDouble();
			double rand2=random2.nextDouble();
			System.out.println("rand1: "+rand1+", rand2: "+rand2);
		}
	}
}
