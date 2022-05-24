<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="unidadServicio">
        <h2 class="jh-entity-heading" data-cy="unidadServicioDetailsHeading">
          <span v-text="$t('pruebaMonoliticaApp.unidadServicio.detail.title')">UnidadServicio</span> {{ unidadServicio.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('pruebaMonoliticaApp.unidadServicio.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ unidadServicio.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('pruebaMonoliticaApp.unidadServicio.unidad')">Unidad</span>
          </dt>
          <dd>
            <div v-if="unidadServicio.unidad">
              <router-link :to="{ name: 'UnidadView', params: { unidadId: unidadServicio.unidad.id } }">{{
                unidadServicio.unidad.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('pruebaMonoliticaApp.unidadServicio.servicio')">Servicio</span>
          </dt>
          <dd>
            <div v-if="unidadServicio.servicio">
              <router-link :to="{ name: 'ServicioView', params: { servicioId: unidadServicio.servicio.id } }">{{
                unidadServicio.servicio.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="unidadServicio.id"
          :to="{ name: 'UnidadServicioEdit', params: { unidadServicioId: unidadServicio.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./unidad-servicio-details.component.ts"></script>
