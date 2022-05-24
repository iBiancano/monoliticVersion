<template>
  <div>
    <h2 id="page-heading" data-cy="ServicioHeading">
      <span v-text="$t('pruebaMonoliticaApp.servicio.home.title')" id="servicio-heading">Servicios</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('pruebaMonoliticaApp.servicio.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ServicioCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-servicio"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('pruebaMonoliticaApp.servicio.home.createLabel')"> Create a new Servicio </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && servicios && servicios.length === 0">
      <span v-text="$t('pruebaMonoliticaApp.servicio.home.notFound')">No servicios found</span>
    </div>
    <div class="table-responsive" v-if="servicios && servicios.length > 0">
      <table class="table table-striped" aria-describedby="servicios">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('pruebaMonoliticaApp.servicio.nombre')">Nombre</span></th>
            <th scope="row"><span v-text="$t('pruebaMonoliticaApp.servicio.duracion')">Duracion</span></th>
            <th scope="row"><span v-text="$t('pruebaMonoliticaApp.servicio.desc')">Desc</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="servicio in servicios" :key="servicio.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ServicioView', params: { servicioId: servicio.id } }">{{ servicio.id }}</router-link>
            </td>
            <td>{{ servicio.nombre }}</td>
            <td>{{ servicio.duracion }}</td>
            <td>{{ servicio.desc }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ServicioView', params: { servicioId: servicio.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ServicioEdit', params: { servicioId: servicio.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(servicio)"
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
        ><span id="pruebaMonoliticaApp.servicio.delete.question" data-cy="servicioDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-servicio-heading" v-text="$t('pruebaMonoliticaApp.servicio.delete.question', { id: removeId })">
          Are you sure you want to delete this Servicio?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-servicio"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeServicio()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./servicio.component.ts"></script>
