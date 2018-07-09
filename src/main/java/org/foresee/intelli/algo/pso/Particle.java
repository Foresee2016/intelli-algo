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
		pBest=new double[dimension];
		pBestFitness = Double.MIN_VALUE;
		for (int i = 0; i < x.length; i++) {
			x[i] = Math.random() * xBound;
			v[i] = Math.random() * vBound;
			pBest[i]=x[i];
		}
	}

}
