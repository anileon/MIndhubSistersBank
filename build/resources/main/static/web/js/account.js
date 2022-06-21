const app = Vue.createApp({

    data() {
        return {
            paramURL: "",
            account: [],
            transactions: [],
            transactionsOrdenadas: [],
            client: []

        }
    },
    created() {
        const params = new Proxy(new URLSearchParams(window.location.search), {
            get: (searchParams, prop) => searchParams.get(prop),
        });
        // Get the value of "some_key" in eg "https://example.com/?some_key=some_value"
        this.paramURL = params.id; // "some_value"

        axios.get('/api/accounts/' + this.paramURL)
            .then(datos => {
                this.account = datos.data;

                this.transactions = datos.data.transactions;
                console.log(this.transactions)

                this.transactionsOrdenadas = this.ordenarTransactions()
            })

        axios.get("/api/clients/current")
            .then(datos => {
                this.client = datos.data
                this.accounts = this.client.accounts
                // console.log (this.client.accounts)
            })

    },

    methods: {
        ordenarTransactions() {
            let auxiliar = this.transactions
            auxiliar.sort((firstId, secondId) => secondId.id - firstId.id);
            return auxiliar

        },

        logOut() {

            axios.post("/api/logout")
                .then(response => console.log("signed out"))
        },

    },

    computed: {

    },

}).mount("#app")


document.addEventListener("DOMContentLoaded", function (event) {

    const showNavbar = (toggleId, navId, bodyId, headerId) => {
        const toggle = document.getElementById(toggleId),
            nav = document.getElementById(navId),
            bodypd = document.getElementById(bodyId),
            headerpd = document.getElementById(headerId)

        // Validate that all variables exist
        if (toggle && nav && bodypd && headerpd) {
            toggle.addEventListener('click', () => {
                // show navbar
                nav.classList.toggle('show')
                // change icon
                toggle.classList.toggle('bx-x')
                // add padding to body
                bodypd.classList.toggle('body-pd')
                // add padding to header
                headerpd.classList.toggle('body-pd')
            })
        }
    }

    showNavbar('header-toggle', 'nav-bar', 'body-pd', 'header')

    /*===== LINK ACTIVE =====*/
    const linkColor = document.querySelectorAll('.nav_link')

    function colorLink() {
        if (linkColor) {
            linkColor.forEach(l => l.classList.remove('active'))
            this.classList.add('active')
        }
    }
    linkColor.forEach(l => l.addEventListener('click', colorLink))

    // Your code to run since DOM is loaded and ready
});


