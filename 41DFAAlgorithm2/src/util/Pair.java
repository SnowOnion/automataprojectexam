package util;

public class Pair<KeyType, ValueType> {
//------Interface---------------------------------------------------------------
	public Pair(KeyType k, ValueType v) {
		_key = k;
		_value = v;
	}
	
	public KeyType key() {
		return _key;
	}
	
	public ValueType value() {
		return _value;
	}
	
	public void setKey(KeyType k) {
		_key = k;
	}
	
	public void setValue(ValueType v) {
		_value = v;
	}
//------Data member-------------------------------------------------------------	
	private KeyType _key;
	private ValueType _value;
}
