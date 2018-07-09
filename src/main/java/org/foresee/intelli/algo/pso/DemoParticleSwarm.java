package org.foresee.intelli.algo.pso;

import java.util.Arrays;

import org.foresee.intelli.algo.pso.ParticleSwarm.ParticleFitnessCalculator;

public class DemoParticleSwarm {
	public static void main(String[] args) {
		int dimension=2;
		ParticleFitnessCalculator fitnessCalculator=new ParticleFitnessCalculator() {
			/**
			 * 适应度计算函数，使用球体的函数，z方=100 - (x-15)方 - (y-12)方，z等于开根式。
			 * 暂时把定义域放在x正轴
			 */
			@Override
			public double calcFitness(double[] x) {
				double temp=100-Math.pow(x[0]-15, 2) - Math.pow(x[1]-12, 2);
				if(temp<0){
					return 0;
				}
				return Math.sqrt(temp);
			}
		};
		ParticleSwarm pso=new ParticleSwarm(fitnessCalculator, 30, dimension, 10, 20, 1, 2, 2);
		int iterateTimes=100;
		while(iterateTimes-->0){
			pso.calcFitness();
			pso.freshVX();
		}
		for (int i = 0; i < pso.particles.length; i++) {
			System.out.println(Arrays.toString(pso.particles[i].x));
		}
	}
}
