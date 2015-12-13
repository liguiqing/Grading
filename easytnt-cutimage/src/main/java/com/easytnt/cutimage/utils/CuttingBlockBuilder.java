/**
 * 
 */
package com.easytnt.cutimage.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.easytnt.grading.domain.cuttings.CuttingBlock;
import com.easytnt.grading.domain.cuttings.CuttingDefine;
import com.easytnt.grading.domain.cuttings.CuttingSolution;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class CuttingBlockBuilder {
	private CuttingSolution cuttingSolution;
	private HashMap<String, List<CuttingDefine>> theSameCuttingDefineMap = new HashMap<>();
	private ArrayList<CuttingBlock> cuttingBlocks = new ArrayList<>();

	public CuttingBlockBuilder(CuttingSolution cuttingSolution) {
		this.cuttingSolution = cuttingSolution;
	}

	public List<CuttingBlock> toBuild() {
		classifyCuttingDefine();
		createCuttingBlockWithTheSameCuttingDefine();
		return cuttingBlocks;
	}

	private void classifyCuttingDefine() {
		List<CuttingDefine> cuttingDefines = cuttingSolution.getCuttingDefines();
		for (CuttingDefine cutDef : cuttingDefines) {
			addToTheSameCuttingDefineMap(cutDef);
		}
	}

	private void addToTheSameCuttingDefineMap(CuttingDefine cutDef) {
		List<CuttingDefine> theSameCuttingDefines = getTheSameCuttingDefinesFromTheCuttingDefineMap(cutDef);
		theSameCuttingDefines.add(cutDef);
	}

	private List<CuttingDefine> getTheSameCuttingDefinesFromTheCuttingDefineMap(CuttingDefine cutDef) {
		List<CuttingDefine> theSameCuttingDefines = theSameCuttingDefineMap.get(cutDef.getName());
		if (theSameCuttingDefines == null) {
			theSameCuttingDefines = new ArrayList<>();
			theSameCuttingDefineMap.put(cutDef.getName(), theSameCuttingDefines);
		}
		return theSameCuttingDefines;
	}

	private void createCuttingBlockWithTheSameCuttingDefine() {
		for (String name : theSameCuttingDefineMap.keySet()) {
			List<CuttingDefine> theSameCuttingDefine = theSameCuttingDefineMap.get(name);
			CuttingBlock cuttingBlock = createCuttingBlock(theSameCuttingDefine, name);
			cuttingBlocks.add(cuttingBlock);
		}
	}

	private CuttingBlock createCuttingBlock(List<CuttingDefine> theSameCuttingDefine, String name) {
		BlockInfo blockInfo = calculateCuttingBlockWidthAndHeight(theSameCuttingDefine);
		sortTheSameCuttingDefineWithPaperPostion(theSameCuttingDefine);
		return new CuttingBlock().setCuttingDefines(theSameCuttingDefine).setName(name).setWidth(blockInfo.width)
				.setHeight(blockInfo.heigth).setKey(blockInfo.key);
	}

	private void sortTheSameCuttingDefineWithPaperPostion(List<CuttingDefine> theSameCuttingDefine) {
		if (theSameCuttingDefine.size() > 1) {
			Collections.sort(theSameCuttingDefine, new Comparator<CuttingDefine>() {

				@Override
				public int compare(CuttingDefine o1, CuttingDefine o2) {
					Integer x1 = o1.getArea().getLeft();
					Integer y1 = o1.getArea().getTop();
					Integer postion1 = o1.getAnswerCardImageIdx();

					Integer x2 = o2.getArea().getLeft();
					Integer y2 = o2.getArea().getTop();
					Integer postion2 = o2.getAnswerCardImageIdx();

					int result = 0;
					if (postion1 == postion2) {
						result = compareX(x1, x2);
						if (result == 0) {
							result = compareY(y1, y2);
						}
					} else {
						result = -1;
					}

					return result;
				}

				private int compareX(int x1, int x2) {
					if (x1 == x2) {
						return 0;
					} else {
						if (Math.abs(x1 - x2) < 30) {
							return 0;
						} else {
							return x1 < x2 ? -1 : 1;
						}
					}
				}

				private int compareY(int y1, int y2) {
					return y1 < y2 ? -1 : 1;
				}
			});
		}
	}

	private BlockInfo calculateCuttingBlockWidthAndHeight(List<CuttingDefine> theSameCuttingDefine) {
		int size = theSameCuttingDefine.size();
		int width = 0;
		int height = 0;
		long key = 0;
		for (CuttingDefine cutDef : theSameCuttingDefine) {
			if (cutDef.getArea().getWidth() > width) {
				width = cutDef.getArea().getWidth();
			}
			height += cutDef.getArea().getHeight();
			key += cutDef.getId();
		}
		// 暂时通过该逻辑来生成一个唯一的key值
		if (size > 1) {
			key = Long.parseLong(size + "" + key);
		}
		return new BlockInfo(width, height, key);
	}

	private class BlockInfo {
		public int width;
		public int heigth;
		private Long key;

		public BlockInfo(int width, int heigth, Long key) {
			this.width = width;
			this.heigth = heigth;
			this.key = key;
		}
	}
}
