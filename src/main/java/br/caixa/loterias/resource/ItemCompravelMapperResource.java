package br.caixa.loterias.resource;

import br.caixa.loterias.mapper.ItemCompravelMapper;
import br.caixa.loterias.model.itemcompravel.ItemCompravel;
import br.caixa.loterias.model.itemcompravel.TipoItemCompravelEnum;
import br.caixa.loterias.repository.remote.impl.ItemCompravelRepository;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/mapper-test/item-compravel")
@Produces(MediaType.APPLICATION_JSON)
public class ItemCompravelMapperResource {

    private final ItemCompravelRepository itemCompravelRepository;
    private final ItemCompravelMapper itemCompravelMapper;

    public ItemCompravelMapperResource(
            ItemCompravelRepository itemCompravelRepository,
            ItemCompravelMapper itemCompravelMapper
    ) {
        this.itemCompravelRepository = itemCompravelRepository;
        this.itemCompravelMapper = itemCompravelMapper;
    }

    @GET
    @Transactional
    public List<ItemCompravelLegadoResponse> listAll() {
        return itemCompravelRepository.listAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GET
    @Path("/{id}")
    @Transactional
    public ItemCompravelLegadoResponse findById(@PathParam("id") Long id) {
        ItemCompravel itemCompravel = itemCompravelRepository.findById(id);

        if (itemCompravel == null) {
            throw new NotFoundException("ItemCompravel not found: " + id);
        }

        return toResponse(itemCompravel);
    }

    @GET
    @Path("/compra/{compraId}")
    @Transactional
    public List<ItemCompravelLegadoResponse> findByCompra(@PathParam("compraId") Long compraId) {
        return itemCompravelRepository.findItensCompraveisByCompra(compraId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ItemCompravelLegadoResponse toResponse(ItemCompravel itemCompravel) {
        return new ItemCompravelLegadoResponse(
                itemCompravel.getId(),
                itemCompravel.getCompra() != null ? itemCompravel.getCompra().getId() : null,
                itemCompravel.getTipo(),
                itemCompravelMapper.toLegado(itemCompravel)
        );
    }

    public record ItemCompravelLegadoResponse(
            Long itemCompravelId,
            Long compraId,
            TipoItemCompravelEnum tipo,
            Object legado
    ) {
    }
}
