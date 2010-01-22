/**
 * 
 */
package cn.edu.thss.retools;

/**
 * 瞬时描述，在NFA运行中保存状态
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
