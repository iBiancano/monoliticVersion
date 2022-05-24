<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="pruebaMonoliticaApp.unidadServicio.home.createOrEditLabel"
          data-cy="UnidadServicioCreateUpdateHeading"
          v-text="$t('pruebaMonoliticaApp.unidadServicio.home.createOrEditLabel')"
        >
          Create or edit a UnidadServicio
        </h2>
        <div>
          <div class="form-group" v-if="unidadServicio.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="unidadServicio.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pruebaMonoliticaApp.unidadServicio.fecha')" for="unidad-servicio-fecha"
              >Fecha</label
            >
            <input
              type="text"
              class="form-control"
              name="fecha"
              id="unidad-servicio-fecha"
              data-cy="fecha"
              :class="{ valid: !$v.unidadServicio.fecha.$invalid, invalid: $v.unidadServicio.fecha.$invalid }"
              v-model="$v.unidadServicio.fecha.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pruebaMonoliticaApp.unidadServicio.unidad')" for="unidad-servicio-unidad"
              >Unidad</label
            >
            <select class="form-control" id="unidad-servicio-unidad" data-cy="unidad" name="unidad" v-model="unidadServicio.unidad">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="unidadServicio.unidad && unidadOption.id === unidadServicio.unidad.id ? unidadServicio.unidad : unidadOption"
                v-for="unidadOption in unidads"
                :key="unidadOption.id"
              >
                {{ unidadOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pruebaMonoliticaApp.unidadServicio.servicio')" for="unidad-servicio-servicio"
              >Servicio</label
            >
            <select class="form-control" id="unidad-servicio-servicio" data-cy="servicio" name="servicio" v-model="unidadServicio.servicio">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  unidadServicio.servicio && servicioOption.id === unidadServicio.servicio.id ? unidadServicio.servicio : servicioOption
                "
                v-for="servicioOption in servicios"
                :key="servicioOption.id"
              >
                {{ servicioOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.unidadServicio.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./unidad-servicio-update.component.ts"></script>
