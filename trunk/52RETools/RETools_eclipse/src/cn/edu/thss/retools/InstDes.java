/**
 * 
 */
package cn.edu.thss.retools;

/**
 * ˲ʱ��������NFA�����б���״̬
 * @author Huhao
 * 
 */
public class InstDes {
	public InstDes(){
	}
	
	public InstDes(AbstractState state, InputSequence inputs) {
		// TODO Auto-generated constructor stub
		this.state = state;
		this.inputs = inputs;
	}
	public AbstractState state;
	public InputSequence inputs;
}
