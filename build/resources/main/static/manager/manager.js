Vue.createApp({
    data() {
        return {
            jsonClientes: [],
            clientes: [],
            firstName: " ",
            lastName: " ",
            email: " ",
            url: " ",
            editName: [],
            editLastNameEdit: [],
            editEmail: [],

        }
    },

    created() {
        axios.get('http://localhost:8080/clients')
            .then(datos => {
                this.jsonClientes = datos.data
                this.clientes = datos.data._embedded.clients

            })

    },

    methods: {
        createClient() {
            axios.post('http://localhost:8080/clients', {
                name: this.firstName,
                lastName: this.lastName,
                mail: this.email,

            })
             .then(function (response) {
                console.log(response)
             })
            .catch(function (error) {
                console.log(error)
            });
        },

        deleteClient(cliente) {
            axios.delete(cliente)
                .then(response => {
                    this.clientes.splice(cliente);
                })
                .then(function (loadData) {
                    location.reload(loadData);
                })

        },

        openModal(cliente) {
            this.editName = cliente.firstName
            this.editLastName = cliente.lastName
            this.editEmail = cliente.email
            this.url = cliente._links.self.href
            console.log(this.url)
        },

        editModal(cliente) {
            axios.patch(this.url, {
                firstName: this.editName,
                lastName: this.editLastName,
                email: this.editEmail,
            })
            .then(response => console.log(response))
            .catch(error => console.log(error))
        }

    },

    computed: {

    },

}).mount("#app")