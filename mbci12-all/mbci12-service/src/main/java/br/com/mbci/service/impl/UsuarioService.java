package br.com.mbci.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.mbci.dao.IUsuarioDAO;
import br.com.mbci.dao.exceptions.DataException;
import br.com.mbci.service.IUsuarioService;
import br.com.mbci.service.exceptions.ServiceException;
import br.com.mbci.service.exceptions.ValidationException;
import br.com.mbci.utils.impls.StringHelper;
import br.com.mbci12.pojo.Usuario;

@Stateless
public class UsuarioService extends GenericService<Usuario, IUsuarioDAO> implements
		IUsuarioService {

	private static final long serialVersionUID = -600482094976415728L;

	private Logger logger = Logger.getLogger(UsuarioService.class);

	@Inject
	private IUsuarioDAO usuarioDAO;
	
	protected IUsuarioDAO getDAO() {
		return usuarioDAO;
	}

	protected void validarSave(Usuario object) throws ValidationException {
		List<String> erros = validaCampos(object);
		String loginDuplicado = loginDuplicado(object);
		if (!loginDuplicado.isEmpty()) {
			erros.add(loginDuplicado);
		}
		if (!erros.isEmpty()) {
			throw new ValidationException(erros);
		}
	}

	protected void validarUpdate(Usuario object) throws ValidationException {
		List<String> erros = validaCampos(object);
		String loginDuplicado = loginDuplicado(object);
		if (!loginDuplicado.isEmpty()) {
			erros.add(loginDuplicado);
		}
		if (!erros.isEmpty()) {
			throw new ValidationException(erros);
		}
	}

	protected void validarDelete(Usuario object) throws ValidationException {
	}

	private List<String> validaCampos(Usuario object) {
		List<String> erros = new ArrayList<String>();
		if (StringHelper.isNullOrEmpty(object.getLogin())) {
			erros.add("O campo login é obrigatório!");
		}
		return erros;
	}

	private String loginDuplicado(Usuario object) throws ValidationException {
		if (logger.isDebugEnabled()) {
			logger.debug("loginDuplicado - object");
			logger.debug("Parâmetros: " + String.format("%s", object));
		}
		String campoDuplicado = "";

		try {
			Usuario usuarioCadastro = new Usuario();
			usuarioCadastro.setIdUsuario(object.getIdUsuario());
			usuarioCadastro.setLogin(object.getLogin());
			List<Usuario> usuarioDataBase = findByExample(usuarioCadastro);

			/**
			 * valida se a consulta retorna algun valor pra o login que será
			 * cadastrado e se vai salvar ou alterar.
			 */
			if (usuarioDataBase != null && !usuarioDataBase.isEmpty()
					&& usuarioCadastro.getLogin() != null) {
				for (Usuario usuario : usuarioDataBase) {
					/**
					 * Se for salvar valida.
					 */
					if (usuarioCadastro.getIdUsuario() == null
							&& usuarioCadastro.getLogin().equals(
									usuario.getLogin().toLowerCase())) {
						campoDuplicado = ("Existe usuario com este login cadastrado!");
					}
					/**
					 * Se for alterar valida.
					 */
					else if (usuarioCadastro.getIdUsuario() != null
							&& !usuarioCadastro.getIdUsuario().equals(usuario.getIdUsuario())
							&& usuarioCadastro.getLogin().equals(
									usuario.getLogin().toLowerCase())) {

						campoDuplicado = ("Login já está definido para outro usuario do sistema!");
					}
				}
			}

		} catch (ServiceException e) {
			logger.error("Erro ao validar usuario na base de dados!", e);
			logger.error("loginDuplicado - object = " + object.toString());
			throw new ValidationException(
					"Erro ao validar usuario na base de dados!");
		}
		return campoDuplicado;
	}

	@Override
	public Boolean usuarioExiste(String login) {
		try {
			Usuario usuario = buscaUsuarioPorLogin(login);
			if (usuario != null && usuario.getIdUsuario() != null) {
				return Boolean.TRUE;
			}
		} catch (ServiceException ex) {
			return Boolean.FALSE;
		}
		return Boolean.FALSE;
	}

	@Override
	public List<Usuario> findByExample(Usuario usuario) throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("findByExample - usuario");
			logger.debug("Parâmetros: " + String.format("%s", usuario));
		}
		try {
			return usuarioDAO.findByExample(usuario);
		} catch (DataException e) {
			logger.error("findByExample - usuario");
			logger.error("Parâmetros: " + String.format("%s", usuario));
			logger.error("Erro ao buscar por example.", e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Usuario save(String logUid, String password) throws ServiceException,
			ValidationException {
		Usuario usuario = new Usuario();

		usuario.setLogin(logUid);
		usuario.setPassword(password);

		save(usuario);

		return usuario;
	}


	
	@Override
	public List<Usuario> findAllAtivos()
			throws ServiceException {
		try {
			return getDAO().findAllAtivos();
		} catch (DataException e) {
			logger.error("Erro ao buscar todos os ativos.", e);
			logger.error("findAllAtivos");
			
			throw new ServiceException(e);
		}
	}

	@Override
	public Usuario buscaUsuarioPorLogin(String login) throws ServiceException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getUsuariosPorLogin - login");
				logger.debug("parametro: login= " + login);
			}
			
			return getDAO().buscaUsuarioPorLogin(login);
		} catch (DataException e) {
			logger.error("getUsuariosPorLogin - login");
			logger.error("Parâmetros: " + String.format("%s", login));
			logger.error("Erro ao buscar usuario por login.", e);
			throw new ServiceException(e);
		}
	}
	

	@Override
	public List<Usuario> buscaUsuarioPorNome(String nome) throws ServiceException {
		if(nome == null
				|| nome.length() == 0){
			throw new ServiceException("É necessário o preencimento do nome do usuario para a pesquisa.");
		}
		try {
			return getDAO().buscaUsuarioPorNome(nome);
		} catch (DataException e) {
			logger.error("getUsuariosPorNome - nomeUsuario");
			logger.error("Parâmetros: " + String.format("%s", nome));
			logger.error("Erro ao buscar usuario por nome.", e);
			throw new ServiceException(e);
		}
		
	}





}
