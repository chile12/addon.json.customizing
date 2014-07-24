package org.springframework.roo.addon.json.customizing;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import flexjson.JSONSerializer;
import flexjson.transformer.AbstractTransformer;

public class ShallowSerializationTransformer extends AbstractTransformer{
	
	private String path;
	
	public ShallowSerializationTransformer(String path)
	{
		this.path = path.endsWith("/") ? path : path + "/";
	}

    public void transform(Object object) 
    {
    	Method getId = null;
		try {
			getId = object.getClass().getMethod("getId");
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
    	if(getId != null)
    	{
    		Long id = null;
			try {
				id = (Long) getId.invoke(object);
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	try {
				getContext().write((new ShallowSerializationObject(object, path + id)).toJson());
			} catch (ShallowSerializationObjectException e) {
				getContext().writeQuoted(e.getMessage());
			}
    	}
    	else
        	getContext().write(new JSONSerializer().serialize(object));
    }
}
