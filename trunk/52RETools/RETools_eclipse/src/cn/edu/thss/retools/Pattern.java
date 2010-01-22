package cn.edu.thss.retools;

/**
 * ģʽƥ��
 * Ĭ�ϲ��ò����Դ�Сд������ƥ��Ĳ���
 * @author Huhao lifengxiang dengshuang
 *
 */
public final class Pattern{

	
	private Automata machine;	//���ɵ�DFA
	private String pattern;
	private boolean compiled = false;	//�Ƿ񾭹�����
	private int caseSensitive = InputSequence.NO_IGNORE_CASE;
	private int matchType = InputSequence.PART_MATCH;
	private int matchLength = InputSequence.LONGEST_MATCH;
	
	
	/**
	 * @return the caseSensitive
	 */
	public int getbCaseSensitive() {
		return caseSensitive;
	}

	/**
	 * @param caseSensitive the caseSensitive to set
	 */
	public void setCaseSensitive(int caseSensitive) {
		if(this.caseSensitive != caseSensitive){
			this.caseSensitive = caseSensitive;
			compiled = false;
		}
	}
	
	public void setMatchLength(int lengthType){
		if(this.matchLength != lengthType){
			this.matchLength = lengthType;
			compiled = false;
		}
	}
	
	public String getPatternString(){
		return pattern;
	}
	
	public AbstractState getStart(){
//		return machine.start;
		return machine.getStart();
	}
	
	public void setMatchType(int matchType){
		if(this.matchType != matchType){
			this.matchType = matchType;
			compiled = false;
		}
	}

	/**
	 * ����Ĭ�Ϲ���Ĺ��캯���������ִ�Сд
	 * @param pattern
	 */
	public Pattern(String pattern){
		this.pattern = pattern;	
	//	fulldfa = this.Compile();
	//	compiled = true;		
	}
	
	/**
	 * arg 3��������ci = 1 ���Դ�Сд�� fm = 1 ��ȫ��ƥ��, ml = 1 �ƥ��
	 * @param pattern
	 * @param args
	 * @throws REFormatException
	 * @throws TransitionNotMatchException
	 */
	public Pattern(String pattern, int[] args) throws REFormatException, TransitionNotMatchException{
		if(args.length == 3){
			caseSensitive = args[0];
			matchType = args[1];
			matchLength = args[2];
		}
		init(pattern);
	}
	
	private void init(String pattern) throws REFormatException, TransitionNotMatchException{
		this.pattern = pattern;
		machine = this.Compile(this.pattern, new int[]{caseSensitive, matchType, matchLength});
		compiled = true;
	}
	
	/**
	 * ci : compile ignore case 
	 * fi : full type or part type
	 * ml : match length
	 * @param pattern
	 * @param args
	 * @return
	 * @throws REFormatException
	 * @throws TransitionNotMatchException
	 */
	public Automata Compile(String pattern, int args[]) throws REFormatException, TransitionNotMatchException {
		int ci = this.caseSensitive;
		int fi = this.matchType;
		
		if(args.length >= 2){
//			throw new ParameterNotMatchException("");
			ci = args[0];
			fi = args[1];
		}
//		int ml = args[2];

		if(fi == InputSequence.FULL_MATCH){
			if(pattern.charAt(0) == InputSequence.startPosition)
				pattern = pattern.substring(1);
		}else if(fi == InputSequence.PART_MATCH){
			if(pattern.charAt(0) == InputSequence.startPosition)
				pattern = pattern.substring(1);
			else
				pattern = ".*(" + pattern + ")";			
		}
		
		AnalysisStack stack;
		InputSequence input;
		RENode rootNode;
		Automata auto;
		Automata dfa = null;
		
		stack = new AnalysisStack();
		input = InputSequence.CreatePatternInput(pattern);//new InputSequence(pattern);
		stack.PushRoot(RENode.CreateRoot());
		
		do{
			RENode cur = stack.Top();
			cur.Transform(stack, input, ci);
		} while(!stack.IsEmpty());
		
		stack.root.Formalize();
		rootNode = stack.root;
		
//		System.out.println(rootNode.PrintNodeWithAttr(0));
		
		auto = new Automata(rootNode, ci);

//		auto.PrintNode();
		dfa = auto.Convert2DFA();
//		dfa.PrintNode();
		
		return dfa;
	}
	
	/**
	 * �������ã�����һ��ƥ��
	 * @param inputs
	 * @return
	 * @throws REFormatException
	 * @throws TransitionNotMatchException
	 */
	public Matcher CreateMatcher(InputSequence inputs) throws REFormatException, TransitionNotMatchException{
		if(!compiled){
			init(pattern);
		}
		
		if(matchType == InputSequence.FULL_MATCH){
			return new FullMatcher(this, inputs);
		}else{
			if(matchLength == InputSequence.LONGEST_MATCH)
				return new PartLongestMatcher(this, inputs);
			else
				return new PartShorestMatcher(this, inputs);
		}			
	}
	
	/**
	 * ��������Զ���
	 */
	public void PrintPattern() {
		// TODO Auto-generated method stub
//		System.out.println("----------Attribute  Tree------");
//		machine.root.node.PrintNodeWithAttr();
//		System.out.println("----------Finish Attribute  Tree------");
		
		machine.PrintNode();		
	}
	
	/**
	 * ��������Զ���
	 */
	public void PrintPatternReverse() {
		machine.PrintNodeReverse();
	}
	
}