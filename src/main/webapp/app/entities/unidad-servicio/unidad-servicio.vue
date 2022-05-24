<template>
  <div>
    <h2 id="page-heading" data-cy="UnidadServicioHeading">
      <span v-text="$t('pruebaMonoliticaApp.unidadServicio.home.title')" id="unidad-servicio-heading">Unidad Servicios</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('pruebaMonoliticaApp.unidadServicio.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'UnidadServicioCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-unidad-servicio"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('pruebaMonoliticaApp.unidadServicio.home.createLabel')"> Create a new Unidad Servicio </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && unidadServicios && unidadServicios.length === 0">
      <span v-text="$t('pruebaMonoliticaApp.unidadServicio.home.notFound')">No unidadServicios found</span>
    </div>
    <div class="table-responsive" v-if="unidadServicios && unidadServicios.length > 0">
      <table class="table table-striped" aria-describedby="unidadServicios">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('pruebaMonoliticaApp.unidadServicio.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('pruebaMonoliticaApp.unidadServicio.unidad')">Unidad</span></th>
            <th scope="row"><span v-text="$t('pruebaMonoliticaApp.unidadServicio.servicio')">Servicio</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="unidadServicio in unidadServicios" :key="unidadServicio.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'UnidadServicioView', params: { unidadServicioId: unidadServicio.id } }">{{
                unidadServicio.id
              }}</router-link>
            </td>
            <td>{{ unidadServicio.fecha }}</td>
            <td>
              <div v-if="unidadServicio.unidad">
                <router-link :to="{ name: 'UnidadView', params: { unidadId: unidadServicio.unidad.id } }">{{
                  unidadServicio.unidad.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="unidadServicio.servicio">
                <router-link :to="{ name: 'ServicioView', params: { servicioId: unidadServicio.servicio.id } }">{{
                  unidadServicio.servicio.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'UnidadServicioView', params: { unidadServicioId: unidadServicio.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'UnidadServicioEdit', params: { unidadServicioId: unidadServicio.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(unidadServicio)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span
          id="pruebaMonoliticaApp.unidadServicio.delete.question"
          data-cy="unidadServicioDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-unidadServicio-heading" v-text="$t('pruebaMonoliticaApp.unidadServicio.delete.question', { id: removeId })">
          Are you sure you want to delete this Unidad Servicio?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-unidadServicio"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeUnidadServicio()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./unidad-servicio.component.ts"></script>
