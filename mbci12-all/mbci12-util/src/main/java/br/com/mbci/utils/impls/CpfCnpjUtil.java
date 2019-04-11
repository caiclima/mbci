package br.com.mbci.utils.impls;


public final class CpfCnpjUtil {

	private static final String cpf = "cpf";
	private static final String cnpj = "cnpj";

	private static final int[] pesoCPF = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
	private static final int[] pesoCNPJ = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

	private CpfCnpjUtil() {

	}

	public static String validaCpfCnpj(String cpfCnpj) {

		String cpfCnpjApenasNumeros = cpfCnpj;

		if (comMascara(cpfCnpj)) {
			cpfCnpjApenasNumeros = formataRemocaoCaracteresEspeciais(cpfCnpj);
		}

		if (cpfCnpjApenasNumeros == null || cpfCnpjApenasNumeros.length() < 9) {
			return null;
		}

		String tipo = ehCpfOuCnpj(cpfCnpjApenasNumeros);

		if (tipo.equals(cpf)) {

			if (cpfCnpjApenasNumeros.length() >= Constantes.TAM_CPF_COMPLETO) {
				cpfCnpjApenasNumeros = cpfCnpj.substring(0, Constantes.TAM_CPF_COMPLETO);
				String digitosVerificadores = cpfCnpjApenasNumeros.substring(cpfCnpjApenasNumeros.length() - 2,
						cpfCnpjApenasNumeros.length());
				if (digitosVerificadores.equals("00")) {
					cpfCnpjApenasNumeros = cpfCnpjApenasNumeros.substring(0, cpfCnpjApenasNumeros.length() - 2);
				}
			}
			if (cpfCnpjApenasNumeros.length() == Constantes.TAM_CPF_SEM_DIGITO_VERIFICADOR) {
				return cpfCnpjApenasNumeros + calcDigVerif(cpfCnpj);
			}
			if (cpfCnpjApenasNumeros.length() == Constantes.TAM_CPF_COMPLETO) {
				return validaCPF(cpfCnpjApenasNumeros) == Boolean.FALSE ? null : cpfCnpjApenasNumeros;
			}
		}

		return cpfCnpjApenasNumeros;

	}

	private static boolean validaCPF(String cpf) {

		String numDig = cpf.substring(0, Constantes.NOVE);
		return calcDigVerif(numDig).equals(cpf.substring(Constantes.NOVE, Constantes.ONZE));
	}

	private static boolean comMascara(String cpfCnpj) {
		if (cpfCnpj.contains(",") || cpfCnpj.contains(".") || cpfCnpj.contains("//") || cpfCnpj.contains("-")) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public static String validaCpfCnpj(Long cpfCnpj) {
		return validaCpfCnpj(String.valueOf(cpfCnpj));
	}

	private static String calcDigVerif(String num) {
		Integer primDig, segDig;
		int soma = 0, peso = Constantes.DEZ;
		for (int i = 0; i < num.length(); i++) {
			soma += Integer.parseInt(num.substring(i, i + Constantes.UM)) * peso--;
		}

		if (soma % Constantes.ONZE == 0 || soma % Constantes.ONZE == Constantes.UM) {
			primDig = Integer.valueOf(0);
		} else {
			primDig = Integer.valueOf(Constantes.ONZE - (soma % Constantes.ONZE));
		}

		soma = 0;
		peso = Constantes.ONZE;
		for (int i = 0; i < num.length(); i++) {
			soma += Integer.parseInt(num.substring(i, i + Constantes.UM)) * peso--;
		}

		soma += primDig.intValue() * Constantes.DOIS;
		if (soma % Constantes.ONZE == 0 || soma % Constantes.ONZE == Constantes.UM) {
			segDig = Integer.valueOf(0);
		} else {
			segDig = Integer.valueOf(Constantes.ONZE - (soma % Constantes.ONZE));
		}

		return primDig.toString() + segDig.toString();
	}

	// CNPJ
	public static String calcDigVerifCnpj(String cnpjParam) {

		String cnpj = formataRemocaoCaracteresEspeciais(cnpjParam);

		int[] arrNum = new int[] { Constantes.CINCO, Constantes.QUATRO, Constantes.TRES, Constantes.DOIS, Constantes.NOVE,
				Constantes.OITO, Constantes.SETE, Constantes.SEIS, Constantes.CINCO, Constantes.QUATRO, Constantes.TRES,
				Constantes.DOIS };

		int soma = 0, divisao = Constantes.ONZE, primDig = 0, segDig = 0;
		for (int i = 0; i < arrNum.length; i++) {
			soma += arrNum[i] * Integer.parseInt(cnpj.substring(i, i + Constantes.UM));
		}

		primDig = (soma % divisao);

		if (primDig < Constantes.DOIS) {
			primDig = 0;
		} else {
			primDig = (Constantes.ONZE - primDig);
		}

		cnpj += primDig;

		arrNum = new int[] { Constantes.SEIS, Constantes.CINCO, Constantes.QUATRO, Constantes.TRES, Constantes.DOIS,
				Constantes.NOVE, Constantes.OITO, Constantes.SETE, Constantes.SEIS, Constantes.CINCO, Constantes.QUATRO,
				Constantes.TRES, Constantes.DOIS };
		soma = 0;
		for (int j = 0; j < arrNum.length; j++) {
			soma += arrNum[j] * Integer.parseInt(cnpj.substring(j, j + Constantes.UM));
		}

		segDig = (soma % divisao);

		if (segDig < Constantes.DOIS) {
			segDig = 0;
		} else {
			segDig = (Constantes.ONZE - segDig);
		}
		return primDig + "" + segDig;
	}

	public static String formataRemocaoCaracteresEspeciais(String valor) {

		if (valor.indexOf(',') != -Constantes.UM || valor.indexOf('.') != -Constantes.UM || valor.indexOf('-') != -Constantes.UM
				|| valor.indexOf('/') != -Constantes.UM) {

			StringBuffer valorAux = new StringBuffer();
			for (int i = 0; i < valor.length(); i++) {
				if ((valor.charAt(i) != ',') && (valor.charAt(i) != '.') && (valor.charAt(i) != '-') && (valor.charAt(i) != '/')) {
					valorAux.append(valor.charAt(i));
				}
			}

			return valorAux.toString();
		}
		return valor;
	}

	private static String ehCpfOuCnpj(String cpfCnpj) {
		if (cpfCnpj != null) {

			if (cpfCnpj.length() == Constantes.TAM_CPF_SEM_DIGITO_VERIFICADOR) {
				return cpf;
			}
			if (cpfCnpj.length() == Constantes.TAM_CPF_COMPLETO) {
				return cpf;
			}

			String valor = cpfCnpj.substring(cpfCnpj.length() - 3, cpfCnpj.length());
			if (valor.equals("000")) {
				return cpf;
			} else {
				return cnpj;
			}
		}
		return null;
	}

	private static int calcularDigito(String str, int[] peso) {
		int soma = 0;
		for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
			digito = Integer.parseInt(str.substring(indice, indice + 1));
			soma += digito * peso[peso.length - str.length() + indice];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}

	/**
	 * Valida se o CPF é valido.
	 * 
	 * @param cpf
	 * @return
	 */
	public static boolean isValidCPF(String cpf) {
		if ((cpf == null) || (cpf.length() != 11)){
			return false;
		}

		Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
		Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
		return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
	}

	/**
	 * Valida se o CNPJ é valido.
	 * 
	 * @param cnpj
	 * @return
	 */
	public static boolean isValidCNPJ(String cnpj) {
		if ((cnpj == null) || (cnpj.length() != 14)){
			return false;
		}

		Integer digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
		Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);
		return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
	}
}
