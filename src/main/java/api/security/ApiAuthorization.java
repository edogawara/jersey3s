package api.security;

import api.exception.ExtendedNotAuthorizedException;


public class ApiAuthorization {
	
	public String userId;
	public String roles;
	
	// Bearer ヘッダーからトークンを取り出す。なければエラー
	public ApiAuthorization(String bearer) {
		final String BEARER = "Bearer ";
	
		if( bearer == null || !bearer.startsWith(BEARER) ) {
			throw new ExtendedNotAuthorizedException("no authorization header!");
		}
		String token = bearer.substring(BEARER.length());

		// ToDo
		// token を解析して権限を取得
	
	}

	// 指定したRole(権限)があるかチェック
	// あれば、True
	public boolean isInRole(String roleName ) {
		
		// Todo 
		
		return true;
	}
	
}
