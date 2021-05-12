public class Hello {
	public static void main(String[] args) {
		int width = 1, length = 2;
		for(int i=0;;i++){
			int area = width*length;
			if(area > 100){
				System.out.println("smallest area > 100: area=" + area + ", length="+length + ", width="+width);
				break;
			}
			width = width*2;
			length = length *2;
		}
	}
}