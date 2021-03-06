package org.foresee.intelli.algo.pso;

import java.util.Arrays;

import org.foresee.intelli.algo.pso.ParticleSwarm.ParticleFitnessCalculator;

/**
 * DemoParticleSwarm2里的是好理解的例子，看着算法应该是对了。
 * 这个适应度计算函数太复杂了，我都不知道哪里是最小值。
 * 没法调试，姑且认为是过了吧，最后适应度到0了，参数值也到0附近了，没法整。
 * TODO：看看这个函数最小值结果是什么，验证答案。
 */
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
//		int iterateTimes=500;
		int iterateTimes=10000;
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
