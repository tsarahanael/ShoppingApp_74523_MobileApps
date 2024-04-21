package com.stu74523.shoppingapp

import com.google.firebase.firestore.FirebaseFirestore
import com.stu74523.shoppingapp.models.Product

fun SeedData(dataBase:FirebaseFirestore) {

    val data: List<Product> = listOf<Product>(
        Product(
            title = "Vintage Denim Jacket",
            price = 49.99f,
            description = "Stay stylish with this vintage-inspired denim jacket. Made with premium denim fabric and featuring distressed details for a rugged look.",
            category = "Jackets",
            image = "https://media.istockphoto.com/id/1444968200/photo/warm-jeans-jacket-isolated-on-white-background.jpg?s=1024x1024&w=is&k=20&c=TyfO4LPx8ey89vvL02Y_zhof3Ot3J7fLukfalDYtZVk="
        ),
        Product(
            title = "Leather Biker Jacket",
            price = 99.99f,
            description = "Rock the biker chic look with this genuine leather jacket. Features a classic asymmetrical zip closure and multiple pockets.",
            category = "Jackets",
            image = "https://media.istockphoto.com/id/638571516/photo/black-leather-biker-jackets.jpg?s=612x612&w=is&k=20&c=HlWqk0mJM__XscZ9H8fEehUzuHMHv2xhhKdD4EvuGnM="
        ),
        Product(
            title = "Quilted Puffer Jacket",
            price = 79.99f,
            description = "Stay warm in style with this quilted puffer jacket. Features a cozy faux fur hood and zippered pockets.",
            category = "Jackets",
            image = "https://media.istockphoto.com/id/1224284457/photo/blue-and-red-full-zipper-windbreaker-down-jacket.jpg?s=612x612&w=is&k=20&c=xFMsGBZNQwItXwKulZy9MC8z0e2MsJeUQ1yJeT4Y6RY="
        ),
        Product(
            title = "Faux Leather Moto Jacket",
            price = 69.99f,
            description = "Channel your inner rebel with this faux leather moto jacket. Designed with quilted panels and metallic hardware.",
            category = "Jackets",
            image = "https://media.istockphoto.com/id/1124441531/photo/leather-jacket-isolated-on-white-background.jpg?s=612x612&w=is&k=20&c=MK-wnZJsCgRMPdmob76KhpHECSF2nwBvihT8wvBy-YU="
        ),
        Product(
            title = "Bomber Jacket",
            price = 59.99f,
            description = "Add some attitude to your look with this bomber jacket featuring cool patches. Ribbed cuffs and collar for a classic bomber style.",
            category = "Jackets",
            image = "https://media.istockphoto.com/id/172183776/photo/lettermans-jacket.jpg?s=612x612&w=is&k=20&c=OUUYEd7qFVudptg3fWGfOrxUTX8fEsGOTuUTp3WVoAQ="
        ),
        Product(
            title = "Hooded Windbreaker",
            price = 49.99f,
            description = "Stay protected from the elements with this hooded windbreaker. Lightweight and water-resistant, perfect for outdoor activities.",
            category = "Jackets",
            image = "https://media.istockphoto.com/id/494727800/photo/yellow-green-windbreaker-waterproof-jacket-full-zip.jpg?s=612x612&w=is&k=20&c=ZK11IDQiMZUUMFDow3yd3cReN-Y46vfeO0MJjOHRRo0="
        ),
        Product(
            title = "Denim Trucker Jacket",
            price = 59.99f,
            description = "A timeless wardrobe staple, this denim trucker jacket adds a rugged yet stylish touch to any outfit. Features chest pockets and button closure.",
            category = "Jackets",
            image = "https://media.istockphoto.com/id/904007732/photo/denim-shirts-jeans.jpg?s=612x612&w=is&k=20&c=WjBNHk52EGMDemcbGZNRfeqsE8Lb4NryiDrGeKTswik="
        ),
        Product(
            title = "Fleece-Lined Parka",
            price = 89.99f,
            description = "Brave the cold with confidence in this fleece-lined parka. Removable faux fur trim and drawstring waist for customizable fit.",
            category = "Jackets",
            image = "https://media.istockphoto.com/id/1188968519/photo/blue-parka-jacket-isolated-on-white.jpg?s=612x612&w=is&k=20&c=COJn-fTaIhWgdb6fzRSuPm8htYMkSYKdDr3XuJpX6wc="
        ),
        Product(
            title = "Camouflage Utility Jacket",
            price = 79.99f,
            description = "Blend into your surroundings with this camouflage utility jacket. Multiple pockets and adjustable waist for functionality and style.",
            category = "Jackets",
            image = "https://media.istockphoto.com/id/135023461/photo/camouflage-jacket.jpg?s=612x612&w=is&k=20&c=XseT-fGEVdpHUN6naMn7IGcJQl88h5xB1akf9vjkDrM="
        ),
        Product(
            title = "Striped Cotton T-Shirt",
            price = 19.99f,
            description = "Add a pop of color to your wardrobe with this striped cotton t-shirt. Made with soft and breathable fabric, perfect for everyday wear.",
            category = "T-Shirts",
            image = "https://media.istockphoto.com/id/827511198/photo/blue-and-white-stripped-sailor-style-t-shirt-isolated.jpg?s=612x612&w=is&k=20&c=9HXUhWRLE0WWWNUu3CVPmulfOqKRflkrOEI7BqosUCA="
        ),
        Product(
            title = "Vintage Logo Graphic Tee",
            price = 29.99f,
            description = "Show off your retro style with this vintage logo graphic tee. Soft cotton fabric and faded print for a worn-in look.",
            category = "T-Shirts",
            image = "https://img.freepik.com/free-photo/pretty-woman-wearing-tshirt_1303-14681.jpg?t=st=1713308983~exp=1713312583~hmac=ef8e14e0e5e2e7d3135cb1dfa295e7ed68b93163e376578984202aeebd2750f8&w=360"
        ),
        Product(
            title = "Striped Pocket T-Shirt",
            price = 19.99f,
            description = "Keep it casual yet stylish with this striped pocket t-shirt. Features a chest pocket and contrast stripes for added flair.",
            category = "T-Shirts",
            image = "https://media.istockphoto.com/id/929918294/vector/striped-t-shirt-on-white-background-vector-illustration.jpg?s=612x612&w=is&k=20&c=Cm5MkD_nfAflUgOuvm9l2pPTivMssPoOoHdcSHcV20s="
        ),
        Product(
            title = "V-Neck Basic Tee",
            price = 14.99f,
            description = "Every wardrobe needs a classic v-neck tee. Made with soft and breathable fabric, perfect for layering or wearing on its own.",
            category = "T-Shirts",
            image = "https://media.istockphoto.com/id/1218949882/photo/black-v-neck-t-shirt-mock-up-on-wooden-hanger-front-and-rear-side-view.jpg?s=612x612&w=is&k=20&c=xDYUCKRSnI96ESj6GK2ET_J0QH-E3t8xObhcCDGBLWQ="
        ),
        Product(
            title = "Tie-Dye Festival Tee",
            price = 34.99f,
            description = "Get groovy with this tie-dye festival tee. Vibrant colors and relaxed fit, perfect for music festivals or casual outings.",
            category = "T-Shirts",
            image = "https://media.istockphoto.com/id/1554362966/photo/tye-dyed-shirt-isolated-on-white.jpg?s=612x612&w=is&k=20&c=aoDFN3JjOepLW_QpsjkOiFJa8GKYc8sHZ2lj2hGjJ-Y="
        ),
        Product(
            title = "Logo Embroidered Polo Shirt",
            price = 39.99f,
            description = "Elevate your casual look with this logo embroidered polo shirt. Classic polo collar and embroidered logo for a refined touch.",
            category = "T-Shirts",
            image = "https://media.istockphoto.com/id/1141430546/photo/shirt-design-and-people-concept-close-up-of-man-in-blank-white-t-shirt-front-and-rear.jpg?s=612x612&w=is&k=20&c=5LIcyx966DnjxKPwAN9BgEGpksDBvDbIGKojrXnJ1U0="
        ),
        Product(
            title = "Graphic Crop Top",
            price = 19.99f,
            description = "Make a statement with this graphic crop top. Cropped silhouette and bold graphics for a trendy and edgy look.",
            category = "T-Shirts",
            image = "https://media.istockphoto.com/id/475887046/photo/studio-shot-of-a-young-lady-on-white-background.jpg?s=612x612&w=is&k=20&c=2i0ijlVkKdI0WCVyY95-D7mRCJQHW0QeNNwIuf3tvdY="
        ),
        Product(
            title = "Long Sleeve Henley Shirt",
            price = 29.99f,
            description = "Upgrade your basics with this long sleeve henley shirt. Button placket and waffle knit fabric for a laid-back yet polished style.",
            category = "T-Shirts",
            image = "https://media.istockphoto.com/id/1348667992/photo/outdoors-portrait-of-young-man-in-new-york-city.jpg?s=612x612&w=0&k=20&c=u1qIbE6mmhC1eOlieNq05zw0fTdMN1U59jjeBWvxY4w="
        ),
        Product(
            title = "Classic Black Jeans",
            price = 39.99f,
            description = "Elevate your denim game with these classic black jeans. Slim fit design with stretchy fabric for all-day comfort.",
            category = "Jeans",
            image = "https://i.pinimg.com/736x/6e/f4/bc/6ef4bc72f9a27a2636f4665f0315e0fc.jpg"
        ),
        Product(
            title = "High-Waisted Skinny Jeans",
            price = 49.99f,
            description = "Flatter your figure with these high-waisted skinny jeans. Stretch denim fabric for a comfortable and flattering fit.",
            category = "Jeans",
            image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9qwbG8gzHkpQibAYPsanDDTnbe2vuOaVkgJxyAAVDVg&s"
        ),
        Product(
            title = "Distressed Boyfriend Jeans",
            price = 59.99f,
            description = "Achieve that effortlessly cool look with these distressed boyfriend jeans. Relaxed fit and distressed details for a laid-back vibe.",
            category = "Jeans",
            image = "https://cdn-img.prettylittlething.com/a/a/8/c/aa8c8c4f398b2971d7107868e98c49cb8cc19c51_cna3252_1.jpg"
        ),
        Product(
            title = "Straight Leg Raw Hem Jeans",
            price = 54.99f,
            description = "Keep it classic with these straight leg raw hem jeans. Raw hem and medium wash for a timeless denim look.",
            category = "Jeans",
            image = "https://i.ebayimg.com/images/g/hecAAOSwAfVlgPCA/s-l1200.webp"
        ),
        Product(
            title = "Cargo Jogger Jeans",
            price = 59.99f,
            description = "Combine comfort and style with these cargo jogger jeans. Cargo pockets and elasticized cuffs for a sporty yet trendy look.",
            category = "Jeans",
            image = "https://i.pinimg.com/736x/d7/c9/bb/d7c9bb6c5ea22c08cfc77ddf5c9461f1.jpg"
        ),
        Product(
            title = "High-Waisted Wide Leg Jeans",
            price = 59.99f,
            description = "Make a fashion statement with these high-waisted wide leg jeans. Wide leg silhouette and high waist for a retro-inspired look.",
            category = "Jeans",
            image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTpR5vjXlApw645mroVWmBAfPsfab0Tzw7KjzhROZi_9g&s"
        ),
        Product(
            title = "Black Skinny Jeans",
            price = 44.99f,
            description = "A versatile staple, these black skinny jeans go with everything. Stretch denim and skinny fit for a sleek and flattering silhouette.",
            category = "Jeans",
            image = "https://media.istockphoto.com/id/679327830/photo/womens-jeans-pants-in-black-isolated-on-white-background.jpg?s=612x612&w=0&k=20&c=zKBh54DCchMX4JG1wbWbGhxZcMzuz4y_qBmXmg3ywUk="
        ),
        Product(
            title = "Ripped Mom Jeans",
            price = 54.99f,
            description = "Get that vintage-inspired look with these ripped mom jeans. High waist and relaxed fit for a comfortable and stylish vibe.",
            category = "Jeans",
            image = "https://i.pinimg.com/originals/10/5e/2f/105e2fc49a752578790aa826f3b96a1f.jpg"
        ),
        Product(
            title = "Cozy Knit Sweater",
            price = 29.99f,
            description = "Stay warm and stylish with this cozy knit sweater. Features a classic crew neck design and ribbed cuffs for a snug fit.",
            category = "Sweaters",
            image = "https://media.istockphoto.com/id/1383561650/photo/yellow-sweater-isolated-on-white-trendy-womens-clothing-knitted-apparel-clothes-jumper.jpg?s=612x612&w=is&k=20&c=Q6pXvmNrr9AlYOKftHb_si_vFQxIsD1QcDlBUw4-fBQ="
        ),
        Product(
            title = "Chunky Cable Knit Sweater",
            price = 59.99f,
            description = "Stay cozy in this chunky cable knit sweater. Features a classic cable knit design and ribbed trim for added texture.",
            category = "Sweaters",
            image = "https://www.shutterstock.com/image-photo/chunky-cable-knit-sweater-jumper-260nw-2436271369.jpg"
        ),
        Product(
            title = "Striped Mohair Sweater",
            price = 69.99f,
            description = "Add some texture to your look with this striped mohair sweater. Soft mohair blend and striped pattern for a cozy yet chic vibe.",
            category = "Sweaters",
            image = "https://media.istockphoto.com/id/827847870/photo/fluffy-woman-s-sweater-in-wide-white-and-purple-stripes-isolated.jpg?s=612x612&w=is&k=20&c=PHgKJ1YlQCwQykDQ-v6Op2hRA1KMQsYRRTMURZHv1NY="
        ),
        Product(
            title = "Off-Shoulder Knit Pullover",
            price = 54.99f,
            description = "Show off your shoulders with this off-shoulder knit pullover. Relaxed fit and ribbed trim for a casual yet stylish look.",
            category = "Sweaters",
            image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQe08RtZFwre85OujCQFI9QKlVc_sh76wwu8aVy74R3cQ&s"
        ),
        Product(
            title = "Fuzzy Cropped Sweater",
            price = 39.99f,
            description = "Stay on-trend with this fuzzy cropped sweater. Cropped length and fuzzy texture for a fun and playful vibe.",
            category = "Sweaters",
            image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQECCmtDjViPl7-XfGVZHEbYpv_WQFs311K3Ivqh3gtiw&s"
        ),
        Product(
            title = "Oversized Wool Blend Sweater",
            price = 69.99f,
            description = "Stay warm and stylish in this oversized wool blend sweater. Wool blend fabric and oversized fit for ultimate comfort.",
            category = "Sweaters",
            image = "https://media.istockphoto.com/id/1278802435/photo/sweater-yellow-color-isolated-on-white-trendy-womens-clothing-knitted-apparel.jpg?s=612x612&w=is&k=20&c=bEcSoE-AGWM16qIJikUrzQHLLaI69VLlM9y7a-WpyHE="
        ),
        Product(
            title = "Cropped Cable Knit Hoodie",
            price = 49.99f,
            description = "Stay cozy and cute in this cropped cable knit hoodie. Cable knit design and cropped length for a trendy and casual look.",
            category = "Sweaters",
            image = "https://www.aeropostale.com/dw/image/v2/BBSG_PRD/on/demandware.static/-/Sites-master-catalog-aeropostale/default/dwd830fedc/82739892_001_main.jpg?sw=2000&sh=2000&sm=fit&sfrm=jpg"
        ),
        Product(
            title = "Printed Scarfs",
            price = 14.99f,
            description = "Accessorize your outfit with this printed scarf. Lightweight and versatile, perfect for adding a pop of pattern to any look.",
            category = "Accessories",
            image = "https://www.shutterstock.com/image-photo/accessory-scarfs-different-textures-colors-260nw-315823712.jpg"
        ),
        Product(
            title = "Leather Crossbody Bag",
            price = 69.99f,
            description = "Elevate your outfit with this chic leather crossbody bag. Compact design with multiple compartments for easy organization.",
            category = "Accessories",
            image = "https://media.istockphoto.com/id/675623820/photo/black-leather-crossbody-bag-isolated-on-white-background.jpg?s=612x612&w=0&k=20&c=w2ukakFzMo1-XvP71zyGZXxTjtZ6-hAILTe9PI1bqFA="
        ),
        Product(
            title = "Classic Aviator Sunglasses",
            price = 39.99f,
            description = "Shield your eyes in style with these classic aviator sunglasses. Metal frame and tinted lenses for a timeless look.",
            category = "Accessories",
            image = "https://media.istockphoto.com/id/1403005009/photo/aviator-sunglasses.jpg?s=612x612&w=0&k=20&c=evY94HWD5xYid4NNaO2c3x2jFL1VyUCszgH9-ghp91M="
        ),
        Product(
            title = "Straw Panama Hat",
            price = 29.99f,
            description = "Stay cool and stylish with this straw panama hat. Wide brim and woven straw construction for a summery vibe.",
            category = "Accessories",
            image = "https://media.istockphoto.com/id/182691429/photo/summer-hat-with-a-brim-and-black-band.jpg?s=612x612&w=is&k=20&c=NqVKC5Mut8Q8zWU7UcdTH5zwL0ZfUr5KI7zhuLhbnnU="
        ),
        Product(
            title = "Printed Silk Scarf",
            price = 49.99f,
            description = "Add a touch of luxury to your ensemble with this printed silk scarf. Versatile and elegant, perfect for accessorizing any outfit.",
            category = "Accessories",
            image = "https://media.istockphoto.com/id/1398690250/photo/portrait-of-young-woman-titivating-hair-wearing-black-dress-silk-kerchief-looking-at-magazine.jpg?s=612x612&w=is&k=20&c=JdZxcBtjGAiAUWU9pEuNzjMZGAxUMurdFeuKsGbv0GU="
        ),
        Product(
            title = "Statement Earrings",
            price = 24.99f,
            description = "Make a statement with these bold statement earrings. Eye-catching design and lightweight construction for comfortable wear.",
            category = "Accessories",
            image = "https://www.shutterstock.com/image-photo/asymmetrical-earrings-polymer-clay-fashion-260nw-2011180277.jpg"
        ),
        Product(
            title = "Leather Belts with Buckle",
            price = 34.99f,
            description = "Complete your look with this stylish leather belt. Features a classic buckle design and adjustable fit for versatility.",
            category = "Accessories",
            image = "https://media.istockphoto.com/id/1371676282/vector/classic-brown-leather-belts-flat-vector-illustrations-set.jpg?s=612x612&w=is&k=20&c=nPC1dBmJ49IRfB0OzWJ6n4wOUAFoJa4g38-lIOqJ74M="
        ),
        Product(
            title = "Patterned Canvas Tote Bag",
            price = 44.99f,
            description = "Carry your essentials in style with this patterned canvas tote bag. Durable canvas fabric and spacious interior for everyday use.",
            category = "Accessories",
            image = "https://sewcanshe.com/wp-content/uploads/2022/06/127_image-asset.jpg"
        ),
        Product(
            title = "Wool Blend Knit Beanie",
            price = 19.99f,
            description = "Keep warm and cozy with this wool blend knit beanie. Classic beanie silhouette and ribbed trim for a snug fit.",
            category = "Accessories",
            image = "https://i.etsystatic.com/6863586/r/il/adbdb9/2110631156/il_570xN.2110631156_e6cd.jpg"
        ),
        Product(
            title = "Studded Leather Wallet",
            price = 49.99f,
            description = "Store your essentials in style with this studded leather wallet. Compact design with multiple card slots and a zippered coin pocket.",
            category = "Accessories",
            image = "https://media.istockphoto.com/id/450426299/photo/money-euro.jpg?s=612x612&w=is&k=20&c=Zh7mCNYpnMVKG7hgvGgEIonf9Gv7HT3etfqGYlVXitk="
        ),

        )

    for(product in data) {
        val db = dataBase.collection("products")
        val query = db.whereEqualTo("title", product.title)
            .get()
            .addOnSuccessListener {
                if (it.isEmpty) db.add(product)
            }
    }
}