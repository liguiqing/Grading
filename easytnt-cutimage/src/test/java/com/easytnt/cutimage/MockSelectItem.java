/**
 * 
 */
package com.easytnt.cutimage;

import java.util.ArrayList;
import java.util.List;

import com.easytnt.grading.domain.cuttings.SelectItem;
import com.easytnt.grading.domain.cuttings.SelectItemArea;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class MockSelectItem {
	public static List<SelectItem> items() {
		ArrayList<SelectItem> list = new ArrayList<>();

		int x = 178;
		int y = 814;

		SelectItem item = new SelectItem();
		list.add(item);
		item.setId(1L).setName("1").setFullScore(1).setAnswer("A").addAreas(createArea("A", x, y, 22, 13))
				.addAreas(createArea("B", x + 37, y, 22, 14)).addAreas(createArea("C", x + (2 * 37), y, 22, 14))
				.addAreas(createArea("D", x + (3 * 37), y, 22, 14));

		y += 1 * 21;
		item = new SelectItem();
		list.add(item);
		item.setId(1L).setName("2").setFullScore(1).setAnswer("A").addAreas(createArea("A", x, y, 22, 13))
				.addAreas(createArea("B", x + 37, y, 22, 14)).addAreas(createArea("C", x + (2 * 37), y, 22, 14))
				.addAreas(createArea("D", x + (3 * 37), y, 22, 14));

		y += 1 * 21;
		item = new SelectItem();
		list.add(item);
		item.setId(1L).setName("3").setFullScore(1).setAnswer("A").addAreas(createArea("A", x, y, 22, 13))
				.addAreas(createArea("B", x + 37, y, 22, 14)).addAreas(createArea("C", x + (2 * 37), y, 22, 14))
				.addAreas(createArea("D", x + (3 * 37), y, 22, 14));

		y += 1 * 21;
		item = new SelectItem();
		list.add(item);
		item.setId(1L).setName("4").setFullScore(1).setAnswer("A").addAreas(createArea("A", x, y, 22, 13))
				.addAreas(createArea("B", x + 37, y, 22, 14)).addAreas(createArea("C", x + (2 * 37), y, 22, 14))
				.addAreas(createArea("D", x + (3 * 37), y, 22, 14));

		return list;
	}

	public static SelectItemArea createArea(String option, int x, int y, int w, int h) {
		SelectItemArea area = new SelectItemArea();
		area.setSelectOption(option);
		area.setLeft(x);
		area.setTop(y);
		area.setWidth(w);
		area.setHeight(h);
		return area;
	}
}
