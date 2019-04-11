package br.com.mbci.utils.impls;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class StringHelper {

	public static boolean isNullOrEmpty(String string) {
		return string == null || string.trim().isEmpty();
	}

	/**
	 * Verifica se a string passada como parÃ¢metro, possui tamanho superior ao
	 * permitido.
	 * 
	 * @param str
	 *            String passada como parÃ¢metro
	 * @param tamanhoMaximo
	 *            Tamanho mÃ¡ximo que a string deve possuir.
	 * @return boolean. (True se a string for maior que o permitido)
	 */
	public static boolean lengthMax(String str, int tamanhoMaximo) {
		return str.length() > tamanhoMaximo ? true : false;
	}

	/**
	 * Se poss&iacute;vel, preenche a string informada com zeros &agrave;
	 * esquerda.
	 * 
	 * @param str
	 *            String a ser preenchida.
	 * @param len
	 *            Tamanho que a string dever&aacute; ter.
	 * @return A string com zeros preenchidos &agrave; esquerda, ou a string
	 *         truncada.
	 */
	public static String fillLeftZeros(String str, int len) {
		if (str != null && str.length() < len) {
			StringBuilder temp = new StringBuilder(len + 1);
			temp.append(str);
			while (temp.length() < len) {
				temp.insert(0, '0');
			}
			return temp.toString();
		} // truncando a string
		else if (str != null) {
			return str.substring(0, len);
		}
		return null;
	}

	/**
	 * Converte o n&uacute;mero informado em uma String e em seguida, se
	 * poss&iacute;vel, o preenche com zeros &agrave; esquerda.
	 * 
	 * @param nro
	 *            N&uacute;mero a ser convertido e preenchido com zeros.
	 * @param len
	 *            Tamanho que a string dever&aacute; ter.
	 * @return A string com zeros preenchidos &agrave; esquerda, ou a string
	 *         truncada.
	 */
	public static String fillLeftZeros(long nro, int len) {
		String str = String.valueOf(nro);
		return fillLeftZeros(str, len);
	}

	/**
	 * Se poss&iacute;vel, preenche a string informada com espa&ccedil;os
	 * &agrave; direita.
	 * 
	 * @param str
	 *            String a preenchida.
	 * @param len
	 *            Tamanho que a string dever&aacute; ter.
	 * @return A string com espa&ccedil;os preenchidos &agrave; direita, ou a
	 *         string truncada.
	 */
	public static String fillRightSpaces(String str, int len) {
		if (str != null && str.length() < len) {
			StringBuilder temp = new StringBuilder(len + 1);
			temp.append(str);
			while (temp.length() < len) {
				temp.append(' ');
			}
			return temp.toString();
		} // truncando a string
		else if (str != null) {
			if ("".equals(str)) {
				return str;
			}
			return str.substring(0, len);
		}

		return null;
	}

	/**
	 * Converte o n&uacute;mero informado em uma String e em seguida, se
	 * possÃ­vel, o preenche com zeros &agrave; direita.
	 * 
	 * @param nro
	 *            N&uacute;mero a ser convertido e preenchido com zeros.
	 * @param len
	 *            Tamanho que a string dever&aacute; ter.
	 * @return A string com zeros preenchidos &agrave; direita, ou a string
	 *         truncada.
	 */
	public static String fillRightZero(String str, int len) {
		if (str != null && str.length() < len) {
			StringBuilder temp = new StringBuilder(len + 1);
			temp.append(str);
			while (temp.length() < len) {
				temp.append('0');
			}
			return temp.toString();
		} // truncando a string
		else if (str != null) {
			if ("".equals(str)) {
				return str;
			}
			return str.substring(0, len);
		}

		return null;
	}

	/**
	 * Se poss&iacute;vel, preenche a string informada com espa&ccedil;os
	 * &agrave; esquerda.
	 * 
	 * @param str
	 *            String a preenchida.
	 * @param len
	 *            Tamanho que a string dever&aacute; ter.
	 * @return A string com espa&ccedil;os preenchidos &agrave; esquerda, ou a
	 *         string truncada.
	 */
	public static String fillLeftSpaces(String str, int len) {
		if (str != null && str.length() < len) {
			StringBuilder temp = new StringBuilder(len + 1);
			temp.append(str);
			while (temp.length() < len) {
				temp.insert(0, ' ');
			}
			return temp.toString();
		} // truncando a string
		else if (str != null) {
			return str.substring(0, len);
		}
		return null;
	}

	/**
	 * Converte um valor double em um String, utilizando a v&iacute;rgula como
	 * separador de casas decimais.
	 * 
	 * @param d
	 *            Valor decimal
	 * @param nroDecimals
	 *            N&uacute;mero de casas decimais.
	 * @return String do valor decimal.
	 */
	public static String doubleToCommaStr(double d, int nroDecimals) {
		String fmt = String.format("%%.%df", nroDecimals);
		java.util.Locale local = new java.util.Locale("pt", "pt_BR");
		return String.format(local, fmt, d);
	}

	/**
	 * Converte um valor double em String, retirando o 'ponto' da casa decimal.
	 * 
	 * @param d
	 *            N&uacute;mero.
	 * @param nroDecimals
	 *            N&uacute;mero de casas decimais.
	 * @return O valor como string, mas sem o separador de casas decimais.
	 */
	public static String doubleToUndottedStr(double d, int nroDecimals) {
		String fmt = String.format("%%.%df", nroDecimals);
		StringBuilder temp = new StringBuilder();
		temp.append(String.format(fmt, d));
		java.text.DecimalFormat format = new java.text.DecimalFormat();
		char separator = format.getDecimalFormatSymbols().getDecimalSeparator();
		int pos = temp.indexOf(String.valueOf(separator));
		if (pos >= 0) {
			temp.deleteCharAt(pos);
		}
		return temp.toString();
	}

	/**
	 * Converte um valor double em String, retirando o 'ponto' da casa decimal.
	 * 
	 * @param d
	 *            N&uacute;mero.
	 * @param nroDecimals
	 *            N&uacute;mero de casas decimais.
	 * @param d
	 *            Valor
	 * @param nroDecimals
	 *            N&ordm; casas decimais.
	 * @param len
	 *            Tamanho final da string.
	 * @return Valor double como string preenchida com zeros &agrave; esquerda.
	 */
	public static String doubleToUndottedStr(double d, int nroDecimals, int len) {
		String temp = doubleToUndottedStr(d, nroDecimals);
		return fillLeftZeros(temp, len);
	}

	public static String retiraAcentos(String acentuada) {
		if (acentuada == null || "".equals(acentuada) || (acentuada.trim().length() <= 0)) {
			return acentuada;
		}
		char[] acentuados = new char[] { 'ç', 'á', 'à', 'ã', 'â', 'ä', 'é', 'è', 'ê', 'ë', 'í', 'ì', 'î', 'ï', 'ó', 'ò', 'õ',
				'ô', 'ö', 'ú', 'ù', 'û', 'ü' };
		char[] naoAcentuados = new char[] { 'c', 'a', 'a', 'a', 'a', 'a', 'e', 'e', 'e', 'e', 'i', 'i', 'i', 'i', 'o', 'o', 'o',
				'o', 'o', 'u', 'u', 'u', 'u' };

		for (int i = 0; i < acentuados.length; i++) {
			acentuada = acentuada.replace(acentuados[i], naoAcentuados[i]);
			acentuada = acentuada.replace(Character.toUpperCase(acentuados[i]), Character.toUpperCase(naoAcentuados[i]));
		}
		return acentuada;
	}

	public static String retiraAcentos(String acentuada, boolean saveNull) {
		if (saveNull && acentuada == null) {
			return "";
		}

		if (acentuada == null || "".equals(acentuada) || (acentuada.trim().length() <= 0)) {
			return acentuada;
		}
		char[] acentuados = new char[] { 'ç', 'á', 'à', 'ã', 'â', 'ä', 'é', 'è', 'ê', 'ë', 'í', 'ì', 'î', 'ï', 'ó', 'ò', 'õ',
				'ô', 'ö', 'ú', 'ù', 'û', 'ü' };
		char[] naoAcentuados = new char[] { 'c', 'a', 'a', 'a', 'a', 'a', 'e', 'e', 'e', 'e', 'i', 'i', 'i', 'i', 'o', 'o', 'o',
				'o', 'o', 'u', 'u', 'u', 'u' };

		for (int i = 0; i < acentuados.length; i++) {
			acentuada = acentuada.replace(acentuados[i], naoAcentuados[i]);
			acentuada = acentuada.replace(Character.toUpperCase(acentuados[i]), Character.toUpperCase(naoAcentuados[i]));
		}
		return acentuada;
	}

	/**
	 * Trunca a string informada.
	 * 
	 * @param str
	 * @param maxTam
	 * @return
	 */
	public static String trunc(String str, int maxTam) {
		if (str == null) {
			return null;
		}
		if (maxTam < 0) {
			maxTam = 0;
		}
		if (str.length() <= maxTam) {
			return str;
		}
		return str.substring(0, maxTam);
	}

	/**
	 * Metodo responsavel por retorna a quantidade de vezes que uma determinada
	 * expressÃ£o esta presente em um string. Busca esta realizada atravÃ©s de
	 * expressÃ£o regular.
	 * 
	 * @param source
	 *            - String onde a busca deve ser realizada.
	 * @param find
	 *            - String contendo uma expressÃ£o que deverÃ¡ ser apricada na
	 *            busca.
	 * @return quantidade de vezes que find foi encontrado em source.
	 */
	public static Integer getOccurrence(String source, String find) {

		Pattern pattern = Pattern.compile(find);
		Matcher matcher = pattern.matcher(source);

		int count = 0;
		while (matcher.find()) {
			count++;
		}

		return count;
	}

	public static Integer getOccurrenceIgnoreCase(String source, String find) {
		return getOccurrence(source.toUpperCase(), find);
	}
}
