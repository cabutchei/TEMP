package br.gov.caixa.mock.sispl.buscaparametros;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/v1/parametros-simulacao")
@Produces(MediaType.APPLICATION_JSON)
public class SisplParametrosResource {

	private static final Logger LOG = LoggerFactory.getLogger(SisplParametrosResource.class);

	@GET
	public ObjectNode getAll() {
		try {
			ObjectNode response = SisplParametrosPayloadSource.load(MqConfig.fromEnvironment());
			LOG.info("Returning {} SISPL parametro(s) for full simulation-parameters request.",
					response.withArray("parametros").size());
			return response;
		} catch (IOException e) {
			throw new InternalServerErrorException("Failed to load SISPL simulation parameters payload.", e);
		}
	}

	@GET
	@Path("/{modalidade}/{numeroConcurso}")
	public ObjectNode getByModalidadeAndConcurso(@PathParam("modalidade") long modalidade,
			@PathParam("numeroConcurso") long numeroConcurso) {
		try {
			MqConfig config = MqConfig.fromEnvironment();
			ObjectNode response = SisplParametrosPayloadSource
					.filter(SisplParametrosPayloadSource.load(config), modalidade, numeroConcurso);
			LOG.info("Returning {} SISPL parametro(s) for modalidade={} concurso={}.",
					response.withArray("parametros").size(), modalidade, numeroConcurso);
			return response;
		} catch (IOException e) {
			throw new InternalServerErrorException("Failed to load SISPL simulation parameters payload.", e);
		}
	}
}
