package org.foresee.intelli.algo.pso;

import java.util.Random;

import org.foresee.intelli.algo.pso.ParticleSwarm.ParticleFitnessCalculator;
/**
 * 改成计算最小值
 */
public class ParticleSwarm2 {
	public final double vBound; // 速度上限
	public final double xBound; // 位置上限
	public final int particleCount;
	public final int dimension;
	public final double w, c1, c2; // 惯性因子，加速常数
	public double[] gBest; // 全局最优值
	public double gBestFitness; // 全局最优值对应的适应度
	Particle[] particles; // 所有粒子
	final ParticleFitnessCalculator fitnessCalculator; // 计算粒子适应度的接口，由外部计算，这样计算公式任意
	Random random; // 为了保证随机值是均匀分布的，使用随机对象来获取随机值
	
	/**
	 * 创建粒子群算法对象
	 * 
	 * @param fitnessCalculator
	 *            计算粒子适应度的接口，由外部计算，这样计算公式任意
	 * @param particleCount
	 *            设定粒子的数目
	 * @param vBound
	 *            速度上限
	 * @param initXBound
	 *            初始化时x的范围
	 * @param w
	 *            惯性因子
	 * @param c1
	 *            加速常数1，与粒子自身历史最优值有关。一般取1到4
	 * @param c2
	 *            加速常数2，与全局最优值有关。一般取1到4
	 */
	public ParticleSwarm2(ParticleFitnessCalculator fitnessCalculator, int particleCount, int dimension, double vBound,
			double xBound, double w, double c1, double c2) {
		this.fitnessCalculator = fitnessCalculator;
		this.xBound = xBound;
		this.vBound = vBound;
		this.particleCount = particleCount;
		this.dimension = dimension;
		this.w = w;
		this.c1 = c1;
		this.c2 = c2;
		initSwarm();
		random = new Random();
	}

	public void initSwarm() {
		particles = new Particle[particleCount];
		for (int i = 0; i < particles.length; i++) {
			particles[i] = new Particle(dimension, xBound, vBound);
			particles[i].pBestFitness=Double.MAX_VALUE;
		}
		gBest = new double[dimension];
		findGBest();
	}

	public void calcFitness() {
		for (int i = 0; i < particles.length; i++) {
			double fit = fitnessCalculator.calcFitness(particles[i].x);
			if (fit < particles[i].pBestFitness) {
				particles[i].pBestFitness = fit;
				for (int d = 0; d < dimension; d++) {
					particles[i].pBest[d] = particles[i].x[d];
				}
			}
		}
		findGBest(); // 这个不放在循环里，单出来清晰
	}

	/**
	 * 取所有粒子里最优值做全局最优值
	 */
	public void findGBest() {
		gBestFitness = Double.MAX_VALUE;
		int bestPos = -1;
		for (int i = 0; i < particles.length; i++) {
			if (particles[i].pBestFitness <= gBestFitness) {
				gBestFitness = particles[i].pBestFitness;
				bestPos = i;
			}
		}
		for (int d = 0; d < dimension; d++) {
			gBest[d] = particles[bestPos].pBest[d];
		}
	}

	// @formatter:off
	/**
	 * 更新粒子的速度和位置。 公式：其中 Vid = w * Vid + C1*random(0,1)(Pid - Xid) + *
	 * C2random(0,1)(Pgd - Xid) Xid = Xid + Vid
	 */
	// @formatter:on
	public void freshVX() {
		for (int i = 0; i < particles.length; i++) {
			for (int d = 0; d < particles[i].v.length; d++) {
				// 保持这样写，调试时方便看到由什么更新成什么了
				double vid = w * particles[i].v[d]
						+ c1 * random.nextDouble() * (particles[i].pBest[d] - particles[i].x[d])
						+ c2 * random.nextDouble() * (gBest[d] - particles[i].x[d]);
				vid = vid > vBound ? vBound : vid;
				vid = vid < -vBound ? -vBound : vid;
				particles[i].v[d] = vid;
				double xid = particles[i].x[d] + vid;
				xid = xid > xBound ? xBound : xid;
				xid = xid < -xBound ? xBound : xid;
				particles[i].x[d] = xid;
			}
		}
	}

}
