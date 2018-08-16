package other.study;

public class Cat extends SAnimal implements IRun {
/*
 *  这里的implements IRun 可写可不写，因为父类已经继承了IRun接口
 *  在使用上没有任何区别。当然写上继承关系，在查看时比较醒目，方便阅读
 * 
 * 
 * */
	
	String sAnimal ="Cat";
//	@Override
	public void jump() {
		// TODO Auto-generated method stub
		System.out.printf("%s Cat jump sysout",sAnimal);
	}
	

}
