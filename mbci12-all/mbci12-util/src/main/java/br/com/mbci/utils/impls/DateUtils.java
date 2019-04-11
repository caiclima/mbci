/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mbci.utils.impls;

import java.util.Date;

public class DateUtils {
    
	private DateUtils(){
		
	}
	
    public static Date safeInstance(Date data) {
        return data == null ? null : new Date(data.getTime());
    }

}
