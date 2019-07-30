package com.pm.bs.product.controller;

public class StorageException extends RuntimeException{

	private static final long serialVersionUID = 901439643269581715L;

	public StorageException(String msg) {
		super(msg);
	}

	public StorageException(String string, Throwable e) {
		super(string, e);
	}
}
