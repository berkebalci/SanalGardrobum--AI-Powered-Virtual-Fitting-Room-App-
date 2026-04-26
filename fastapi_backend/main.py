from fastapi import FastAPI
from gradio_client import Client, handle_file
from pydantic import BaseModel

app = FastAPI()

# Veri modeli (Android'den ne bekliyoruz?)
class TryOnRequest(BaseModel):
    person_url: str
    garment_url: str

@app.post("/generate-tryon")
def tryon_endpoint(request: TryOnRequest):
    client = Client("yisol/IDM-VTON")
    
    # Senin paylaştığın o sihirli Gradio kodu:
    result = client.predict(
        dict={
            "background": handle_file(request.person_url),
            "layers": [],
            "composite": None
        },
        garm_img=handle_file(request.garment_url),
        garment_des="T-shirt", # Burayı dinamik yapabilirsin
        is_checked=True,
        is_checked_crop=False,
        denoise_steps=30,
        seed=42,
        api_name="/tryon"
    )
    
    # Result genellikle üretilen dosyanın yolunu döner.
    # Bu yolu Android'e string olarak gönderiyoruz.
    return {"result_url": result}

# Çalıştırmak için terminale: uvicorn main:app --reload