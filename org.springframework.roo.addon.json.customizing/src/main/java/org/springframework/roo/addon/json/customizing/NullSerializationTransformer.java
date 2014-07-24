package org.springframework.roo.addon.json.customizing;

import flexjson.JSONSerializer;
import flexjson.transformer.*;

public class NullSerializationTransformer extends AbstractTransformer {

	NullSerialization serializeNull = NullSerialization.NULL;
	String defaultValue;
	
	public NullSerializationTransformer(NullSerialization serializeNull) throws NullSerializationException
	{
		if(serializeNull == NullSerialization.DEFAULT)
			throw new NullSerializationException("NullSerialization.DEFAULT needs a default value");
		this.serializeNull = serializeNull;
	}
	
	public NullSerializationTransformer(NullSerialization serializeNull, String defaultValue) throws NullSerializationException
	{
		if(serializeNull == NullSerialization.DEFAULT){
			this.serializeNull = serializeNull;
			this.defaultValue = defaultValue;
		}
		else
			throw new NullSerializationException("a default value needs NullSerialization.DEFAULT");
	}

    public void transform(Object object) 
    {
    	if (object == null) 
    	{
			if (this.serializeNull == NullSerialization.DEFAULT) {
				getContext().writeQuoted(defaultValue);
			} else if (this.serializeNull == NullSerialization.EMPTY) {
				getContext().writeQuoted("");
			} else if (this.serializeNull == NullSerialization.NULL) {
				getContext().write("null");
			}
		}
    	else
    		getContext().write(new JSONSerializer().serialize(object));
    }
    
    @Override
    public Boolean isInline() {
    	if(serializeNull == NullSerialization.REMOVE)
    		return Boolean.TRUE;
    	else
    		return Boolean.FALSE;
    }
}
