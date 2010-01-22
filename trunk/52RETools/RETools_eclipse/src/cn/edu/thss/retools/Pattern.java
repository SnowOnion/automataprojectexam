package cn.edu.thss.retools;

/**
 * 模式匹配
 * 默认采用不忽略大小写，部分匹配的策略
 * @author Huhao lifengxiang dengshuang
 *
 */
public final class Pattern{

	
	private Automata machine;	//生成的DFA
	private String pattern;
	private boolean compiled = false;	//是否经过编译
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
	 * 采用默认规则的构造函数，即区分大小写
	 * @param pattern
	 */
	public Pattern(String pattern){
		this.pattern = pattern;	
	//	fulldfa = this.Compile();
	//	compiled = true;		
	}
	
	/**
	 * arg 3个参数，ci = 1 忽略大小写， fm = 1 非全文匹配, ml = 1 最长匹配
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
	 * 根据配置，创建一个匹配
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
	 * 正向输出自动机
	 */
	public void PrintPattern() {
		// TODO Auto-generated method stub
//		System.out.println("----------Attribute  Tree------");
//		machine.root.node.PrintNodeWithAttr();
//		System.out.println("----------Finish Attribute  Tree------");
		
		machine.PrintNode();		
	}
	
	/**
	 * 反向输出自动机
	 */
	public void PrintPatternReverse() {
		machine.PrintNodeReverse();
	}
	
}