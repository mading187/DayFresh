package fresh.util;

public class Stringutil {

	public static boolean checkNull(String ... strs) {
		if(strs == null || strs.length <=0) {
			return true;
		}
		
		for(String str : strs) {
			if(str == null || "".contentEquals(str)){
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean checkNull(Object ... strs) {
		if(strs == null || strs.length <=0) {
			return true;
		}
		
		for(Object str : strs) {
			if(str == null ){
				return true;
			}
		}
		
		return false;
	}
}
