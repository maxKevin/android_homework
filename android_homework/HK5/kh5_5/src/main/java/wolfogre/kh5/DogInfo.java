package week5.kh5;

import week5.hk5.R;

/**
 * Created by Jason Song(wolfogre@outlook.com) on 02/06/2016.
 */
public class DogInfo {
	public String name;
	public int resourceId;

	public DogInfo(String name, int resourceId) {
		this.name = name;
		this.resourceId = resourceId;
	}

	public static String[] getDogNames(){
		return new String[]{
				"柴犬",
				"藏獒",
				"吉娃娃",
				"哈士奇",
				"巴哥犬",
				"松狮犬",
				"德国牧羊犬",
				"法国斗牛犬",
				"英国古代牧羊犬",
				"阿拉斯加雪橇犬",
		};
	}

	public static int getResourceId(String dogName){
		switch (dogName){
			case "柴犬":
				return R.drawable.cq;
			case "藏獒":
				return R.drawable.za;
			case "吉娃娃":
				return R.drawable.jww;
			case "哈士奇":
				return R.drawable.hsq;
			case "巴哥犬":
				return R.drawable.bgq;
			case "松狮犬":
				return R.drawable.ssq;
			case "德国牧羊犬":
				return R.drawable.dgmyq;
			case "法国斗牛犬":
				return R.drawable.fgdnq;
			case "英国古代牧羊犬":
				return R.drawable.yggdmyq;
			case "阿拉斯加雪橇犬":
				return R.drawable.alsjxqq;
		};
		return 0;
	}
}
