package org.foresee.intelli.algo.pso;

public class Particle {
	double[] x; // 粒子的位置
	double[] v; // 粒子的速度
	double[] pBest; // 个体极值
	double pBestFitness; // 个体极值对应的适应度

	public Particle() {
	}

	public Particle(int dimension, double xBound, double vBound) {
		super();
		x = new double[dimension];
		v = new double[dimension];
		pBest = new double[dimension];
		for (int i = 0; i < x.length; i++) {
			x[i] = (Math.random() - 0.5) * 2 * xBound; // 生成可以为负值
			v[i] = (Math.random() - 0.5) * 2 * vBound;
			pBest[i] = x[i];
		}
	}

}
