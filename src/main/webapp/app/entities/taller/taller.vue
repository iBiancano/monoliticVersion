<template>
  <div>
    <h2 id="page-heading" data-cy="TallerHeading">
      <span v-text="$t('pruebaMonoliticaApp.taller.home.title')" id="taller-heading">Tallers</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('pruebaMonoliticaApp.taller.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'TallerCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-taller"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('pruebaMonoliticaApp.taller.home.createLabel')"> Create a new Taller </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && tallers && tallers.length === 0">
      <span v-text="$t('pruebaMonoliticaApp.taller.home.notFound')">No tallers found</span>
    </div>
    <div class="table-responsive" v-if="tallers && tallers.length > 0">
      <table class="table table-striped" aria-describedby="tallers">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('pruebaMonoliticaApp.taller.marca')">Marca</span></th>
            <th scope="row"><span v-text="$t('pruebaMonoliticaApp.taller.modelo')">Modelo</span></th>
            <th scope="row"><span v-text="$t('pruebaMonoliticaApp.taller.matricula')">Matricula</span></th>
            <th scope="row"><span v-text="$t('pruebaMonoliticaApp.taller.color')">Color</span></th>
            <th scope="row"><span v-text="$t('pruebaMonoliticaApp.taller.numSerie')">Num Serie</span></th>
            <th scope="row"><span v-text="$t('pruebaMonoliticaApp.taller.generacion')">Generacion</span></th>
            <th scope="row"><span v-text="$t('pruebaMonoliticaApp.taller.unidadservicio')">Unidadservicio</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="taller in tallers" :key="taller.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TallerView', params: { tallerId: taller.id } }">{{ taller.id }}</router-link>
            </td>
            <td>{{ taller.marca }}</td>
            <td>{{ taller.modelo }}</td>
            <td>{{ taller.matricula }}</td>
            <td>{{ taller.color }}</td>
            <td>{{ taller.numSerie }}</td>
            <td>{{ taller.generacion }}</td>
            <td>
              <div v-if="taller.unidadservicio">
                <router-link :to="{ name: 'UnidadServicioView', params: { unidadServicioId: taller.unidadservicio.id } }">{{
                  taller.unidadservicio.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TallerView', params: { tallerId: taller.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TallerEdit', params: { tallerId: taller.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(taller)"
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
        ><span id="pruebaMonoliticaApp.taller.delete.question" data-cy="tallerDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-taller-heading" v-text="$t('pruebaMonoliticaApp.taller.delete.question', { id: removeId })">
          Are you sure you want to delete this Taller?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-taller"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeTaller()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./taller.component.ts"></script>
