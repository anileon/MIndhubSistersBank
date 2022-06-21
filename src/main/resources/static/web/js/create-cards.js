const app = Vue.createApp({

    data() {
        return {
            client: [],
            cards: [],
            debit: [],
            credit: [],
            cardType: "",
            cardColor: "",
            debitCard: "",
            creditCard: "",

        }
    },

    created() {
        axios.get("/api/clients/current")
            .then(datos => {
                this.client = datos.data
                this.cards = datos.data.cards
                this.debit = this.cards.filter(debito => debito.type == "DEBITO")
                this.credit = this.cards.filter(credito => credito.type == "CREDITO")
            })
    },


    methods: {
        modifiedDate(CardDate) {
            let date = new Date(CardDate)
            let month = date.getMonth() + 1
            month = month.toString().padStart(2, "0")
            let year = date.getFullYear()
            year = year - 2000

            return month + "/" + year
        },

        modifiedNumber(number) {
            let part1 = number.toString().slice(0, 4)
            let part2 = number.toString().slice(4, 8)
            let part3 = number.toString().slice(8, 12)
            let part4 = number.toString().slice(12, 16)

            return part1 + "-" + part2 + "-" + part3 + "-" + part4

            console.log(number)
            console.log(part1)
            console.log(part2)
        },

        logOut() {

            axios.post("/api/logout")
                .then(response => window.location.href = "/web/login.html")

        },

        CreateCard() {
            console.log(this.cardColor)
            console.log(this.cardType)
            axios.post("/api/clients/current/cards", `color=${this.cardColor}&type=${this.cardType}`, {
                headers: { 'content-type': 'application/x-www-form-urlencoded' }
            })
                .then(response => window.location.href = "/web/cards.html")
        }

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