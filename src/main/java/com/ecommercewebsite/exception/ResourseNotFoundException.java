package com.ecommercewebsite.exception;

public class ResourseNotFoundException extends RuntimeException
{
  public ResourseNotFoundException()
  {
	  super();
  }
  public ResourseNotFoundException(String message)
  {
	  super(message);
  }
}
