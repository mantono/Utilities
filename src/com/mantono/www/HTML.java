package com.mantono.www;

public interface HTML
{
	CharSequence getDocType();
	CharSequence getHead(String path);
	CharSequence getBody(String path);
}
