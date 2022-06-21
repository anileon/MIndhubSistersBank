const app = Vue.createApp({

    data() {
        return {
            name: "",
            select: [],
            newSelect: [],
            accounts: [],
            client: [],
            amount: 0,
            description: "",
            accountOr: "",
            accountDe: "",
        }
    },

    created() {
        axios.get("/api/clients/current")
            .then(datos => {
                this.client = datos.data
                this.accounts = this.client.accounts
                this.accounts = datos.data.accounts.sort((firstId, secondId) => firstId.id - secondId.id)
                this.name = datos.data.firstName

            })
    },

    methods: {
        logOut() {
            axios.post("/api/logout")
                .then(response => window.location.href = "/web/login.html")
        },

        sendTransfer() {
            Swal.fire({
                title: 'Do you want to make this transaction?',
                showDenyButton: true,
                // showCancelButton: true,
                denyButtonText: `Cancel`,
                confirmButtonText: 'Send',             
            }).then((result) => {
                if (result.isConfirmed) {
                    axios.post("/api/client/current/transactions", `amount=${this.amount}&description=${this.description}&newAccountOrigen=${this.accountOr}&newAccountDestiny=${this.accountDe}`, {
                        headers: { 'content-type': 'application/x-www-form-urlencoded' }
                    })
                        .then(response => {
                            Swal.fire('Sent!', '', 'success')
                                .then(result => {
                                    window.location.reload()
                                })
                                .catch(error => {
                                    Swal.fire('Transaction failed', error.response.data, 'error')
                                        .then(result => {
                                            window.location.reload()
                                        })
                                })
                        })
                } else if (result.isDenied) {
                    Swal.fire('Changes are not saved', '', 'error')
                }
            })
        }
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