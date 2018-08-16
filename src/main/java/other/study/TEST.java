package other.study;

public class TEST {

	
	
/*
 * 父类实现了一个接口，那么其子类也是归属于该接口，
*  在接口多态的使用过程中，完全可以使用这个 子类实例进行传递。
 *比如下面的ptf（）方法，iRun指向了cat类实例，
 *说明cat类也是算IRun的实现类。
 *当然，子类也可以写上实现接口，在查看继承实现关系时，也一目了然。
 *  *  * * 
 * */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IRun iRun = new SAnimal();
		ptf(iRun);
		iRun = new Cat();
		ptf(iRun);
	}
	
	public static void ptf(IRun iRun) {
		iRun.run();
	}
}
