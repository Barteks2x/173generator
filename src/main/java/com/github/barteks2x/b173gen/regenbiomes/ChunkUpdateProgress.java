package com.github.barteks2x.b173gen.regenbiomes;

class ChunkUpdateProgress {
	private int max;
	private int done;

	ChunkUpdateProgress() {
	}

	void increment() {
		++done;
	}

	int getMax() {
		return max;
	}

	int getDone() {
		return done;
	}

	void setMax(int size) {
		this.max = size;
	}

	String getMessage() {
		return "Generating biomes progress: " + done + "/" + max + " (" + 100 * done / (double) max + "%)";
	}

}
