package com.leigq.quartz;

public class Test {

	public static void main(String[] args) {
		System.out.println(getMessage("我爱吃{}和{}", "萝卜", "青菜"));
		System.out.println(getMessage("我爱吃{}和111{}", "萝卜", "青菜"));
	}

	private static String getMessage(String msg, String... params) {
		String[] strings = msg.split("\\{}");
		for (String string : strings) {
			System.out.println(string);
		}

		for (String param : params) {
			msg = msg.replaceFirst("\\{}", param);
		}

		return msg;
	}

}
