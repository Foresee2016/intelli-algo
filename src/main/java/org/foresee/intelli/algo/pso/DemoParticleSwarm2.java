package org.foresee.intelli.algo.pso;

import java.util.Arrays;

import org.foresee.intelli.algo.pso.ParticleSwarm.ParticleFitnessCalculator;

/**
 * 哇！这个居然真的算出来了，虽然是最简单的一个变量。 x^3 - 5*x^2 - 2*x +
 * 3，求导赋0得出拐点在-0.18附近是极大值，和3.5附近是极小值。
 * 这里取范围-5到+5，极大值就是最大值，解应该是-0.18附近，确实有很多已经靠近这个值了。
 */
public class DemoParticleSwarm2 {
	public static void main(String[] args) {
		ParticleFitnessCalculator fitnessCalculator = new ParticleFitnessCalculator() {
			/**
			 * 适应度计算函数，使用网上例程里的函数 f(x) = x^3 - 5*x^2 - 2*x + 3
			 */
			@Override
			public double calcFitness(double[] x) {
				return Math.pow(x[0], 3) - 5 * Math.pow(x[0], 2) - 2 * x[0] + 3;
			}
		};
		// ParticleSwarm pso=new ParticleSwarm(fitnessCalculator, 30, 20, 60,
		// 100, 0.9, 1, 1.2);
		ParticleSwarm pso = new ParticleSwarm(fitnessCalculator, 30, 1, 4, 5, 0.9, 1, 1.2);
		// int iterateTimes=500; // 取500的时候，大部分解靠近了最大位置的解，有几个还离得远。
		int iterateTimes = 10000; // 取更大的，就都靠近最大位置了。
		while (iterateTimes-- > 0) {
			pso.calcFitness();
			pso.freshVX();
			if (iterateTimes % 100 == 0) { // 输出太多，前边的被覆盖了，跳一截输出一次，能看到减小的过程。
				System.out.println(pso.gBestFitness);
			}
		}
		System.out.println("------");
		for (int i = 0; i < pso.particles.length; i++) {
			System.out.println(Arrays.toString(pso.particles[i].x));
		}
	}
}