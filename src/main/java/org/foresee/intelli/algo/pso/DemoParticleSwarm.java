package org.foresee.intelli.algo.pso;

import java.util.Arrays;

import org.foresee.intelli.algo.pso.ParticleSwarm.ParticleFitnessCalculator;

public class DemoParticleSwarm {
	public static void main(String[] args) {
		ParticleFitnessCalculator fitnessCalculator=new ParticleFitnessCalculator() {
			/**
			 * 适应度计算函数，使用网上例程里的函数
			 * f(x) = sum[i=1-N](xi*xi/4000) - 连乘[i=1-N]cos(xi/sqrt(i)) + 1
			 */
			@Override
			public double calcFitness(double[] x) {
				double temp1=0;
				for (int i = 0; i < x.length; i++) {
					temp1+=x[i]*x[i] / 4000;
				}
				double temp2=1;
				for (int i = 0; i < x.length; i++) {
					temp2=temp2*Math.cos(x[i]/Math.sqrt(i+1));
				}
				return temp1-temp2+1;
			}
		};
//		ParticleSwarm pso=new ParticleSwarm(fitnessCalculator, 30, 20, 60, 100, 0.9, 1, 1.2);
		ParticleSwarm2 pso=new ParticleSwarm2(fitnessCalculator, 30, 2, 60, 100, 0.9, 1, 1.2);
		int iterateTimes=500;
		while(iterateTimes-->0){
			pso.calcFitness();
			pso.freshVX();
			System.out.println(pso.gBestFitness);
		}
		System.out.println("------");
		for (int i = 0; i < pso.particles.length; i++) {
			System.out.println(Arrays.toString(pso.particles[i].x));
		}
	}
}
