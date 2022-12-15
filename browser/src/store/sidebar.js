import { defineStore } from 'pinia';

export const useSidebarStore = defineStore('Sidebar.vue', {
	state: () => {
		return {
			collapse: false
		};
	},
	getters: {},
	actions: {
		handleCollapse() {
			this.collapse = !this.collapse;
		}
	}
});
