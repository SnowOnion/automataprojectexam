package cn.edu.thss.retools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;


/**
 * 正则表达式文法中的所有终结符以及非终结符的节点的抽象基类
 * @author Huhao lifengxiang dengshuang
 *
 */
public abstract class RENode implements Cloneable { 
	
	public static final int NO_TYPE = 0;
	public static final int SELECT_STAR_CLOSURE_TYPE = 1;	// []*
	public static final int SELECT_Question_CLOSURE_TYPE = 2;	// []?
	public static final int SELECT_POSITIVE_CLOSURE_TYPE = 3; // []+
	public static final int STAR_CLOSURE_TYPE = 4;	// ()*
	public static final int Question_CLOSURE_TYPE = 5;	// ()?
	public static final int POSITIVE_CLOSURE_TYPE = 6; // ()+
	public static final int OR_TYPE = 7;	// |
	public static final int LINK_TYPE = 8;	//
	public static final int SELECT_TYPE = 9;	//[]
	public static final int REVERSE_SELECT_TYPE = 10; //[^]
	
	protected String label;		//节点的值，对于非终结符，没有用，但不能为“”
	protected int type;		//节点的属性值
	protected RENode parent;
	protected ArrayList<RENode> children;
	
	public static RENode CreateRoot(){
		return new ENode();
	}
	
	protected RENode(RENode cp){
		this.label = cp.label;
		this.type = cp.type;
		this.parent = cp.parent;
		this.children = cp.children;
	}
	
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the parent
	 */
	public RENode getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(RENode parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	public ArrayList<RENode> getChildren() {
		return children;
	}	
	
	public RENode () {
		label = "";
		type = LINK_TYPE;
		parent = this;
		children = new ArrayList<RENode>();
	}
	
	public int CountOfChildren() {
		return children.size();
	}
	public boolean IsTerminal() {
		return false;
	}
	
	public void AppendChild(RENode child) {
		children.add(child);
		child.parent = this;
	}
	
	public RENode ChildAt(int index) {
		return children.get(index);
	}
	
	public void RemoveChild(RENode node) {
		children.remove(node);
		node.setParent(node);
	}
	
	public void ChangeToEpsilon(){
		this.label = "";
	}
	
	public void ReplaceChild(RENode child, RENode newchild) {
		int id = children.indexOf(child);
		if(id >= 0) {
			children.remove(id);
			children.add(id, newchild);//.set(id - 1, newchild);
			newchild.parent = this;
		}
 	}
	
	/**
	 * 将 . 替换成 所有字符
	 * @param child
	 * @param als
	 */
	public void ReplaceChild(MetaNode child, Set<AlphaNode> als) {
		// TODO Auto-generated method stub
		int id = children.indexOf(child);
		if(id >= 0){
			children.remove(id);
			Iterator<AlphaNode> it = als.iterator();
			while(it.hasNext()){
				AlphaNode al = it.next();
				children.add(id, al);
				al.parent = this;
			}			
		}
	}
	
	/*
	 * remove child with it's children
	 */
	public void ReplaceWithChildren(RENode child) {
		Iterator<RENode> iter = child.children.iterator();
		this.RemoveChild(child);
		while(iter.hasNext()) {
			this.AppendChild((RENode)iter.next());
		}
	}
	
	public boolean Match(char c, int ignore) {
		char fi = this.label.charAt(0);
		char se = c;
		if(ignore == InputSequence.IGNORE_CASE){
			fi = Character.toLowerCase(fi);
			se = Character.toLowerCase(se);
		}
		if(IsTerminal() && fi == se)
			return true;
		return false;
	}
	
	public void CutDuplication() throws REFormatException {
//		RENode par = this.parent;
//		if(this.IsEpsilon()) {
//			if(par != this) {
//				par.RemoveChild(this);
//			}			
//		}
//		if(par != this && par.CountOfChildren() == 1) {
//			par.ReplaceWithChildren(this);
//		}
	}
	
	public void Formalize() throws REFormatException{
		this.CutDuplication();
	}
	
	public boolean IsEpsilon() {
		return !IsTerminal() && this.label == "";
	}
	
	public boolean IsMeta(){
		return false;
	}
	
	public boolean AllAreNonTerminal(){
		Iterator<RENode> iter = children.iterator();
		while(iter.hasNext()){
			if(iter.next().IsTerminal())
				return false;
		}
		return true;
	}
	
	public boolean AllAreTerminal(){
		Iterator<RENode> iter = this.children.iterator();
		while(iter.hasNext()){
			if(!iter.next().IsTerminal())
				return false;
		}
		return true;
	}
	
	public boolean ChangeType(){
		if(this.CountOfChildren() == 1){
			RENode child = this.ChildAt(0);
			if(!child.IsTerminal() && this.parent != this && child.type != REVERSE_SELECT_TYPE
			&& (this.type == OR_TYPE || this.type == LINK_TYPE || this.type == SELECT_TYPE)){
				this.type = child.type;
				return true;
			}
		}
		return false;
	}
	
	public void RepaireParentRelation(){
		Iterator<RENode> iter = children.iterator();
		while(iter.hasNext()){
			RENode st = iter.next();
			st.setParent(this);
		}
	}
	
	/**
	 * 判断当前节点是否可以被其子节点替换
	 * @return
	 */
	public boolean IsReplaceable() {
		boolean res = false;
		if(this.parent == this || this.IsTerminal() || this.IsMeta() || this.CountOfChildren() <= 0){
			return res;
		} else {	
			switch(parent.type){
			case LINK_TYPE:
				if(this.type == LINK_TYPE){
					res = true;
				}
				break;
			case OR_TYPE:
				if(this.type == OR_TYPE )//|| (this.CountOfChildren() == 1 && type == LINK_TYPE))
					res = true;
				break;
			case SELECT_TYPE:
				if(this.type == SELECT_TYPE || this.type == LINK_TYPE)
					res = true;
				break;
			case SELECT_STAR_CLOSURE_TYPE:
				if(this.type == SELECT_STAR_CLOSURE_TYPE || this.type == SELECT_POSITIVE_CLOSURE_TYPE 
					|| this.type == OR_TYPE || this.type == SELECT_TYPE)
					res = true;
				break;
			case SELECT_Question_CLOSURE_TYPE:
				if(this.type == SELECT_Question_CLOSURE_TYPE || this.type == OR_TYPE || this.type == SELECT_TYPE)
					res = true;
				break;
			case SELECT_POSITIVE_CLOSURE_TYPE:
				if(this.type == SELECT_POSITIVE_CLOSURE_TYPE || this.type == OR_TYPE || this.type == SELECT_TYPE)
					res = true;
				break;
			case STAR_CLOSURE_TYPE:
				if(this.type == STAR_CLOSURE_TYPE || this.type == POSITIVE_CLOSURE_TYPE 
					|| this.type == OR_TYPE || this.type == SELECT_TYPE)
					res = true;
				break;
			case Question_CLOSURE_TYPE:
				if(this.type == Question_CLOSURE_TYPE || this.type == OR_TYPE || this.type == SELECT_TYPE)
					res = true;
				break;
			case POSITIVE_CLOSURE_TYPE:
				if(this.type == POSITIVE_CLOSURE_TYPE || this.type == OR_TYPE || this.type == SELECT_TYPE)
					res = true;
				break;
			case REVERSE_SELECT_TYPE:
				if(this.type == SELECT_TYPE/* || this.type == REVERSE_SELECT_TYPE*/)
					res = true;
				break;
			default:
					break;
			}
		}
		return res;
	}
	
	public void PrintNode() {
		String res = "";
		res += this.label + " ";
		Iterator<RENode> iter = this.children.iterator();
		while(iter.hasNext()) {
			res += iter.next().label;
		}
		System.out.println(res);
		
		iter = this.children.iterator();
		while(iter.hasNext()) {
			iter.next().PrintNode();
		}				
	}

	public String PrintNodeWithAttr(){
		String res = "";
		Iterator<RENode> iter = children.iterator();
		res += this.label + " " + this.type + " \n";
		while(iter.hasNext()) {
			res += iter.next().PrintNodeWithAttr();
		}
		return res; 
	}

	public String PrintNodeWithAttr(int dep){
		String res = "";
		String nodeIn = "";
		Iterator<RENode> iter = children.iterator();
		if(this.IsMeta())
			nodeIn += "M";
		if(this.IsTerminal())
			nodeIn += "A";
		res += Integer.toString(dep) + " " + this.label + " " + this.type + " " + nodeIn + " \n";
		while(iter.hasNext()) {
			res += iter.next().PrintNodeWithAttr(dep+1);
		}
		return res; 
	}
	
	public void ClearType(){
		this.type = NO_TYPE;
	}
	
	/**
	 * 构建语法树
	 * @param stack
	 * @param input
	 * @param para Pattern.IGNORE_CASE = 1, Pattern.NO_IGNORE_CASE = 0
	 * @throws REFormatException
	 */
	public abstract void Transform(AnalysisStack stack, InputSequence input, int para) throws REFormatException;
	
	public abstract Object clone();
	
}

/**
 * AlphaNode表示所有的终结符，不包括元字符
 * @author Huhao
 *
 */
class AlphaNode extends RENode {
	
	public AlphaNode() {
		super();
		init();
	}
	
	public AlphaNode(AlphaNode an){
		super(an);
	}
	
	private void init(){
		this.label = "";
		this.type = NO_TYPE;
	}
	public AlphaNode(String lbl) {
		init();
		this.label = lbl;
	}
	
	public boolean IsTerminal() {
		return true;
	}
	
	public void Transform(AnalysisStack stack, InputSequence input, int para) throws REFormatException {
		char ch = input.Getc();
		stack.Pop();
		if(!Match(ch, para))
			throw new REFormatException();
	}
	
	public void PrintNode() {
//		return this.label;
		System.out.println(this.label);
	}
	
	/*
	 * 
	 * @see cn.edu.thss.retool.RENode#Format()
	 */
	public void CutDuplication() {
		//nothing
	}
	
	public Object clone(){		
		return new AlphaNode(this);
	}
}

/**
 * 代表所有的元字符，也是终结符
 * @author Huhao
 *
 */
class MetaNode extends RENode {
	public MetaNode() {
		super();
		init();
	}
	
	public MetaNode(MetaNode mn){
		super(mn);
	}
	
	private void init(){
		this.label = "";
		this.type = NO_TYPE;
	}
	
	public MetaNode(String lbl) {
		this.type = NO_TYPE;
		this.label = lbl;
	}
	
	public boolean IsTerminal() {
		return true;
	}
	
	public boolean IsMeta(){
		return true;
	}
	
	public void Transform(AnalysisStack stack, InputSequence input, int para) throws REFormatException {
		char ch = input.Getc();
		stack.Pop();
		if(!Match(ch, para))
			throw new REFormatException();
	}
	
	public void PrintNode() {
//		return this.label;
		System.out.println(this.label);
	}
	
	/*
	 * 
	 * @see cn.edu.thss.retool.RENode#Format()
	 */
	public void CutDuplication() {
//		String anything = Character.valueOf(InputSequence.anything).toString();
//		if(this.getLabel().equals(anything)){
//			Set<AlphaNode> als = new HashSet<AlphaNode>();
//			Iterator<Character> it = InputSequence.acceptChars.iterator();
//			while(it.hasNext()){
//				als.add(new AlphaNode(Character.toString(it.next())));
//			}
//			it = InputSequence.metaChars.iterator();
//			while(it.hasNext()){
//				als.add(new AlphaNode(Character.toString(it.next())));
//			}
//			this.parent.ReplaceChild(this, als);			
//		}
		 
	}
	
	public Object clone(){		
		return new MetaNode(this);
	}
}

class ENode extends RENode {
	public ENode() {
		super();
		this.label = "E";
		this.type = OR_TYPE;
	}
	
	public ENode(ENode an){
		super(an);
	}
	
	public void Transform(AnalysisStack stack, InputSequence input, int para) throws REFormatException  {
		char ch = input.Peek();
		stack.Pop();
//		if(Character.isDigit(ch) || Character.isLetter(ch) || ch == '(' || ch == '[' || ch == '.') {
		if(input.needEscape() || InputSequence.isRealInput(ch) || ch == '(' || ch == '[' || ch == '.'){
			RENode f = new FNode();
			RENode h = new HNode();
			this.AppendChild(f);
			this.AppendChild(h);
			stack.Push(h);
			stack.Push(f);
		}
		else {
			throw new REFormatException("E : " + ch);
		}
	}
	
	public void CutDuplication() throws REFormatException {	
		for(;;){
			boolean change = false;
			change = Cut();
			if(!change)
				break;
		}
//		Cut();
	}
	
	public boolean IsReplaceable(){
		return super.IsReplaceable();
	}
	
	private boolean Cut() throws REFormatException{
		boolean changed = false;
		for(int i = 0; i < this.children.size();) {
			RENode child = this.ChildAt(i);
			if(child.IsEpsilon()) {
				this.RemoveChild(child);
			} else {
				child.CutDuplication();
				i ++;
			}
		}
		ArrayList<RENode> newChildren = new ArrayList<RENode>();
		Iterator<RENode> iter = this.children.iterator();
		
		changed = this.ChangeType();
		
		while(iter.hasNext()) {
			RENode child = iter.next();
			//can't remove FNode, it will cause ambigorous, for example, (12|21)* and (1221)*
			if(child.IsReplaceable()) {
				newChildren.addAll(child.children);
				changed = true;
			} else {
				newChildren.add(child);
			}				
		}				
		
		this.children = newChildren;
		
		RepaireParentRelation();
		return changed;
	}
	
	public void Formalize() throws REFormatException{
		this.CutDuplication();
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return new ENode(this);
	}
}

class HNode extends RENode {
	public HNode() {
		super();
		this.label = "H";
		this.type = OR_TYPE;
	}
	
	public HNode(HNode an){
		super(an);
	}
	
	public void Transform(AnalysisStack stack, InputSequence input, int para) throws REFormatException  {
		char ch = input.Peek();
		stack.Pop();
		if(!input.needEscape()){
			switch(ch) {
			case '|':
				input.Getc(); //抛弃
				RENode newE = new ENode();
//				this.AppendChild(newE);
				stack.Push(newE);
				this.parent.ReplaceChild(this, newE);
				break;
			case ')':
//				this.label = "";
				this.ChangeToEpsilon();
				break;
			case '$':
//				this.label = "";
				this.ChangeToEpsilon();
				break;
			default :
				throw new REFormatException("H : " + ch);
			}
		}else{
			throw new REFormatException("H : " + ch);
		}
		
	}
	
	//never run
	public void CutDuplication() throws REFormatException {			
//		Cut();
	}
	
	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return new HNode(this);
	}
}

class GNode extends RENode {
	public GNode() {
		super();
		this.label = "G";
		this.type = NO_TYPE;
	}
	
	public GNode(GNode an){
		super(an);
	}
	
	public void Transform(AnalysisStack stack, InputSequence input, int para) throws REFormatException   {
		char ch = input.Peek();
		stack.Pop();
//		if (Character.isDigit(ch) || Character.isLetter(ch) || ch == '(' || ch == '['
		if(input.needEscape() || InputSequence.isRealInput(ch) || ch == '(' || ch == '['
			|| ch == '|' || ch == ')' || ch == '$' || ch == '.') {
//			this.label = "";
			this.ChangeToEpsilon();
		} else if (ch == '*' || ch == '+' || ch == '?') {
//			AlphaNode an = new AlphaNode(Character.toString(ch));
			MetaNode an = new MetaNode(Character.toString(ch));
//			this.AppendChild(an);
			this.setLabel(Character.toString(ch));
			stack.Push(an);
		} else {
			throw new REFormatException("G : " + ch);
		}
	}
	
	public void CutDuplication() throws REFormatException {
		throw new REFormatException("G : Format!");
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return new GNode(this);
	}
}

/*
 * 废弃的
 */
class ANode extends RENode {
	public ANode() {
		super();
		this.label = "A";
//		this.type = LINK_TYPE;
		this.type = SELECT_TYPE;
	}
	
	public ANode(ANode an){
		super(an);
	}
	
	//be careful A need recurisively set the type of it's children
//	public void setType(int type){
//		this.type = type;
//		Iterator<RENode> iter = children.iterator();
//		while(iter.hasNext()){
//			iter.next().setType(type);
//		}
//	}
	
	public void Transform(AnalysisStack stack, InputSequence input, int para) throws REFormatException    {
		char ch = input.Peek();
		stack.Pop();
		if (Character.isDigit(ch) || Character.isLetter(ch) || ch == '.') {
			RENode an = new AlphaNode(Character.toString(ch));
			RENode b = new BNode();
			this.AppendChild(an);
			this.AppendChild(b);
			stack.Push(b);
			stack.Push(an);
		} else {
			throw new REFormatException("A : " + ch);
		}
	}
	
	public void CutDuplication() throws REFormatException {
		for(int i = 0; i < children.size();) {
			RENode child = children.get(i);
			if(child.IsEpsilon()) {
				children.remove(i);
			} else {
				if(!child.IsTerminal())
					child.CutDuplication();
				i ++;
			}				
		}
		
//		boolean changed = this.ChangeType();
		
		ArrayList<RENode> newChildren = new ArrayList<RENode>();
		Iterator<RENode> iter = children.iterator();
		while(iter.hasNext()) {
			RENode child = iter.next();
			if(child.IsReplaceable()) {
				newChildren.addAll(child.children);
			} else {
				newChildren.add(child);
			}
		}			
		
		this.children = newChildren;
		RepaireParentRelation();
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return new ANode(this);
	}
}

/*
 * 废弃的
 */
class BNode extends RENode {
	public BNode() {
		super();
		this.label = "B";
		this.type = LINK_TYPE;
	}
	
	public BNode(BNode an){
		super(an);
	}
	
	public void Transform(AnalysisStack stack, InputSequence input, int para) throws REFormatException    {
		char ch = input.Peek();
		stack.Pop();
		if (Character.isDigit(ch) || Character.isLetter(ch) || ch == '.') {
			RENode a = new ANode();
			this.parent.ReplaceChild(this, a);
			stack.Push(a);
		} else if(ch == ']') {
			this.label = "";
		} else {
			throw new REFormatException("B : " + ch);
		}
	}
	
	public void CutDuplication() throws REFormatException {
		//never run
		ArrayList<RENode> newChildren = new ArrayList<RENode>();
		Iterator<RENode> iter = children.iterator();
		while(iter.hasNext()) {
			RENode child = iter.next();
			if(!child.IsTerminal())
				child.CutDuplication();
			if(child.IsReplaceable()) {
				newChildren.addAll(child.children);
			} else {
				newChildren.add(child);
			}
		}	
		
		this.children = newChildren;
		RepaireParentRelation();
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return new BNode(this);
	}
}

class DNode extends RENode {
	public DNode() {
		super();
		this.label = "D";
		this.type = SELECT_TYPE;
	}
	
	public DNode(DNode an){
		super(an);
	}
	
	public void Transform(AnalysisStack stack, InputSequence input, int para) throws REFormatException    {
		char ch = input.Peek();
		stack.Pop();
		if(!input.needEscape() && ch == '^'){
			RENode mn = new MetaNode(Character.toString(ch));
			RENode r = new RNode();			
			this.AppendChild(r);
			r.setType(REVERSE_SELECT_TYPE);			//取反类型s	
			stack.Push(r);
			stack.Push(mn);
//		} else  if (Character.isDigit(ch) || Character.isLetter(ch)) {
		}else if(input.needEscape() || InputSequence.isRealInput(ch)){
			RENode r = new RNode();
			this.parent.ReplaceChild(this, r);
			stack.Push(r);
		} else {
			throw new REFormatException("D : " + ch);
		}
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return new DNode(this);
	}
	
	public void CutDuplication() throws REFormatException{
		CutReverse();
		
		ArrayList<RENode> newChildren = new ArrayList<RENode>();
		for(int i = 0; i < children.size(); ) {
			RENode child = children.get(i);
			if(child.IsEpsilon()) {
				children.remove(i);
			} else {				
				child.CutDuplication();								
				i ++;
			}
		}
		
		this.ChangeType();
		
		for(int i = 0; i < children.size(); i++) {
			RENode child = children.get(i);
			if(child.IsReplaceable()) {
				newChildren.addAll(child.children);
			} else {
				newChildren.add(child);
			}	
		}			
		
		this.children = newChildren;
		
		RepaireParentRelation();	
	}
	
	private void CutReverse(){
		if(ChildAt(0).IsMeta()){
			String rev = ChildAt(0).getLabel();
			if(rev.equals("^")){
				children.remove(0);			
			}
		}
	}
}

class RNode extends RENode {
	public RNode() {
		super();
		this.label = "R";
		this.type = SELECT_TYPE;
	}
	
	public RNode(RNode an){
		super(an);
	}
	
	public void Transform(AnalysisStack stack, InputSequence input, int para) throws REFormatException    {
		char ch = input.Peek();
		stack.Pop();
//		if (Character.isDigit(ch) || Character.isLetter(ch)) {
		if(input.needEscape() || InputSequence.isRealInput(ch)){
			RENode al = new AlphaNode(Character.toString(ch));
			RENode u = new UNode();
			this.AppendChild(al);
			this.AppendChild(u);
			stack.Push(u);
			stack.Push(al);
		} else {
			throw new REFormatException("R : " + ch);
		}
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return new RNode(this);
	}
	
	public void CutDuplication() throws REFormatException {			
		CutLinkSymbol();
		
		ArrayList<RENode> newChildren = new ArrayList<RENode>();
		for(int i = 0; i < children.size(); ) {
			RENode child = children.get(i);
			if(child.IsEpsilon()) {
				children.remove(i);
			} else {				
				child.CutDuplication();								
				i ++;
			}
		}
		
		for(int i = 0; i < children.size(); i++) {
			RENode child = children.get(i);
			if(child.IsReplaceable()) {
				newChildren.addAll(child.children);
			} else {
				newChildren.add(child);
			}	
		}			
		
		this.children = newChildren;
		RepaireParentRelation();
	}
	
	private void CutLinkSymbol() throws REFormatException{
		int size = this.CountOfChildren();
		if(size == 4){
			RENode fn = this.ChildAt(1);
//			String lbl = children.get(1).getLabel();
			if(fn.IsMeta() && fn.getLabel().equals("-")) {
				children.remove(1);
				char from = children.get(0).getLabel().charAt(0);
				char to = children.get(1).getLabel().charAt(0);
				char min, max;
				max = from > to ? from : to;
				min = from > to ? to : from;
				Character  t = Character.valueOf(max);
				t --;
				while(t != min){
					children.add(1, new AlphaNode(t.toString()));
					t --;
				}				
			}
		}
	}
}

class UNode extends RENode {
	public UNode(){
		super();
		this.label = "U";
		this.type = LINK_TYPE;
	}
	
	public UNode(UNode an){
		super(an);
	}
	
	public void Transform(AnalysisStack stack, InputSequence input, int para) throws REFormatException    {
		char ch = input.Peek();
		stack.Pop();
		if(!input.needEscape() && ch == '-'){
			input.Getc();
			RENode al = new MetaNode(Character.toString(ch));
			
			ch = input.Peek();
			RENode toa = new AlphaNode(Character.toString(ch));
			RENode t = new TNode();
			this.parent.ReplaceChild(this, al);
			this.parent.AppendChild(toa);
			this.parent.AppendChild(t);
			stack.Push(t);
			stack.Push(toa);
//		} else if (Character.isDigit(ch) || Character.isLetter(ch) || ch == ']') {
		}else if(input.needEscape() || InputSequence.isRealInput(ch) || ch == ']'){
			RENode t = new TNode();
			this.parent.ReplaceChild(this, t);
			stack.Push(t);
		} else {
			throw new REFormatException("U : " + ch);
		}
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return new UNode(this);
	} 
	
	public void CutDuplication() throws REFormatException{
		//never get here
		throw new REFormatException("U CutDuplication!");
	}
}

class TNode extends RENode {
	public TNode(){
		super();
		this.label = "T";
		this.type = SELECT_TYPE;
	}
	
	public TNode(TNode an){
		super(an);
	}
	
	public void Transform(AnalysisStack stack, InputSequence input, int para) throws REFormatException    {
		char ch = input.Peek();
		stack.Pop();
//		if (Character.isDigit(ch) || Character.isLetter(ch)) {
		if(input.needEscape() || InputSequence.isRealInput(ch)){
			RENode r = new RNode();
			this.parent.ReplaceChild(this, r);
			stack.Push(r);
		} else if(!input.needEscape() && ch == ']') {
//			this.label = "";
			this.ChangeToEpsilon();
		} else {
			throw new REFormatException("T : " + ch);
		}
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return new TNode(this);
	} 
	
	public void CutDuplication() throws REFormatException{
		throw new REFormatException("T : cutDuplication ");
	}
}

class FNode extends RENode {
	public FNode() {
		super();
		this.label = "F";
		this.type = LINK_TYPE;
		this.cutFlag = 0;
	}
	
	public FNode(FNode an){
		super(an);
	}
	
	public boolean IsReplaceable() {
		String plbl = this.parent.getLabel();
		if(plbl == "F"){
			return super.IsReplaceable();
		}else if(plbl == "H"){
			if(AllAreTerminal())
				return false;
		}
		return super.IsReplaceable();
	}
	
	public void Transform(AnalysisStack stack, InputSequence input, int para) throws REFormatException     {
		char ch = input.Peek();
		stack.Pop();
		if(!input.needEscape() && ch == '(') {
//			RENode l = new AlphaNode("(");
			RENode l = new MetaNode("(");
			RENode m = new ENode();
//			RENode r = new AlphaNode(")");
			RENode r = new MetaNode(")");
			RENode g = new GNode();
			RENode n = new NNode();
			this.AppendChild(l);
			this.AppendChild(m);
			this.AppendChild(r);
			this.AppendChild(g);
			this.AppendChild(n);
			stack.Push(n);
			stack.Push(g);
			stack.Push(r);
			stack.Push(m);
			stack.Push(l);
		} else if(!input.needEscape() && ch == '[') {
			RENode l = new MetaNode("[");
			RENode m = new DNode();
			RENode r = new MetaNode("]");
			RENode g = new GNode();
			RENode n = new NNode();
			this.AppendChild(l);
			this.AppendChild(m);
			this.AppendChild(r);
			this.AppendChild(g);
			this.AppendChild(n);
			stack.Push(n);
			stack.Push(g);
			stack.Push(r);
			stack.Push(m);
			stack.Push(l);
		}else if(!input.needEscape() && ch == '.'){
			RENode a = new MetaNode(Character.toString(ch));
			RENode g = new GNode();
			RENode n = new NNode();
			this.AppendChild(a);
			this.AppendChild(g);
			this.AppendChild(n);
			stack.Push(n);
			stack.Push(g);
			stack.Push(a);	
		}else if(input.needEscape() || InputSequence.isRealInput(ch)) {
			RENode a = new AlphaNode(Character.toString(ch));
			RENode g = new GNode();
			RENode n = new NNode();
			this.AppendChild(a);
			this.AppendChild(g);
			this.AppendChild(n);
			stack.Push(n);
			stack.Push(g);
			stack.Push(a);		
		} else {
			throw new REFormatException("F : " + ch);
		}
	}

	public void CutDuplication() throws REFormatException {
		CutBracket();
		for(;;){
			boolean change = false;
			change = Cut();
			if(!change)
				break;
		}
//		Cut();
	}
	
	private void CutBracket() throws REFormatException{
		if(cutFlag == 0){
			int size = children.size();
			if(size == 3)  { // aGN 
				String glbl = children.get(1).getLabel();
				RENode alphaNode = children.get(0);
				if(glbl.equals("")) {
//					alphaNode.setType(LINK_TYPE);
				} else if (glbl.equals("*")) {
					alphaNode.setType(STAR_CLOSURE_TYPE);
				} else if (glbl.equals("+")) {
					alphaNode.setType(POSITIVE_CLOSURE_TYPE);
				} else if (glbl.equals("?")) {
					alphaNode.setType(Question_CLOSURE_TYPE);
				} else {
					throw new REFormatException("F format : no * ? + space");
				}
				children.remove(1);	//remove G
			} else if (size == 5) {	//(E)GN  or  [D]GN
				String glbl = children.get(3).getLabel();
				RENode edNode = children.get(1);
				if(children.get(0).getLabel() == "(") {
					if(glbl.equals("")) {
						edNode.setType(OR_TYPE);
					} else if (glbl.equals("*")) {
						edNode.setType(STAR_CLOSURE_TYPE);
					} else if (glbl.equals("+")) {
						edNode.setType(POSITIVE_CLOSURE_TYPE);
					} else if (glbl.equals("?")) {
						edNode.setType(Question_CLOSURE_TYPE);
					} else {
						throw new REFormatException("F format : no * ? + space");
					}
				} else if (children.get(0).getLabel() == "[") {
					if(glbl.equals("")) {
						if(edNode.getType() != REVERSE_SELECT_TYPE){
							 edNode.setType(SELECT_TYPE);
						}
					} else if (glbl.equals("*")) {
						edNode.setType(SELECT_STAR_CLOSURE_TYPE);
					} else if (glbl.equals("+")) {
						edNode.setType(SELECT_POSITIVE_CLOSURE_TYPE);
					} else if (glbl.equals("?")) {
						edNode.setType(SELECT_Question_CLOSURE_TYPE);
					} else {
						throw new REFormatException("F format : no * ? + space");
					}
				}			
				children.remove(3);	//remove G
				children.remove(2);
				children.remove(0);
			} else {
				throw new REFormatException("F format : size not 3 or 5");
			}
			
			cutFlag = 1;
		}
	}
	
	public boolean Cut() throws REFormatException {
		boolean changed = false;
		
		ArrayList<RENode> newChildren = new ArrayList<RENode>();
		for(int i = 0; i < children.size(); ) {
			RENode child = children.get(i);
			if(child.IsEpsilon()) {
				children.remove(i);
			} else {				
				child.CutDuplication();								
				i ++;
			}
		}
		
		changed = this.ChangeType();
		
		for(int i = 0; i < children.size(); i++) {
			RENode child = children.get(i);
			if(child.IsReplaceable()) {
				newChildren.addAll(child.children);
				changed = true;
			} else {
				newChildren.add(child);
			}	
		}			
		
		this.children = newChildren;
		RepaireParentRelation();
		return changed;
	}
	
	private int cutFlag;

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return new FNode(this);
	}
}

class NNode extends RENode {
	public NNode() {
		super();
		this.label = "N";
		this.type = LINK_TYPE; 
	}
	
	public NNode(NNode an){
		super(an);
	}
	
	public void Transform(AnalysisStack stack, InputSequence input, int para) throws REFormatException {
		char ch = input.Peek();
		stack.Pop();
//		if (Character.isDigit(ch) || Character.isLetter(ch)
		if(input.needEscape() || InputSequence.isRealInput(ch)
			|| ch == '.' || ch == '(' || ch == '[') {
			RENode f = new FNode();
			this.parent.ReplaceChild(this, f);
			stack.Push(f);
		} else if (ch == ')' || ch == '|' || ch == '$') {
//			this.label = "";
			this.ChangeToEpsilon();
		} else {
			throw new REFormatException("N : " + ch);
		}
	}
	
	public void CutDuplication() throws REFormatException {
		//never run
		Iterator<RENode> iter = children.iterator();
		ArrayList<RENode> newChildren = new ArrayList<RENode>();
		while(iter.hasNext()) {
			RENode child = iter.next();
			child.CutDuplication();
		}
		
		this.ChangeType();
		
		iter = children.iterator();
		while(iter.hasNext()) {
			RENode child = iter.next();
			if(child.IsReplaceable()) {
				newChildren.addAll(child.children);
			} else {
				newChildren.add(child);
			}
		}		
		
		this.children = newChildren; 	
		RepaireParentRelation();
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return new NNode();
	}
}

