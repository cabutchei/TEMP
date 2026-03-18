package br.gov.caixa.silce.servico.conversor.compra;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public abstract class JsonIdentityInfoMixin {}
